package com.rainiq.designservice.service;

import com.rainiq.designservice.client.PropertyClient;
import com.rainiq.designservice.dto.DesignRequest;
import com.rainiq.designservice.dto.DesignResponse;
import com.rainiq.designservice.entity.Design;
import com.rainiq.designservice.entity.Review;
import com.rainiq.designservice.entity.ReviewStatus;
import com.rainiq.designservice.event.DesignSubmittedEvent;
import com.rainiq.designservice.exception.InvalidRequestException;
import com.rainiq.designservice.exception.ResourceNotFoundException;
import com.rainiq.designservice.repo.DesignRepository;
import com.rainiq.designservice.repo.ReviewRepository;
import com.rainiq.designservice.topic.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesignService {
    private final DesignRepository designRepository;
    private final ReviewRepository reviewRepository;
    private final PropertyClient propertyClient;
    private final KafkaTemplate<String, DesignSubmittedEvent>kafkaTemplate;
    private void triggerKafka(UUID designId, UUID propertyId)
    {
        DesignSubmittedEvent designSubmittedEvent=DesignSubmittedEvent.builder()
                .designId(designId)
                .propertyId(propertyId).build();
      kafkaTemplate.send(KafkaTopics.DESIGN_SUBMITTED,designId.toString(),designSubmittedEvent);
    }
    @Transactional
    public DesignResponse submitDesign(DesignRequest designRequest, String userEmail)
    {
        String token = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest()
                .getHeader("Authorization").substring(7);

        if (!propertyClient.propertyExists(designRequest.getPropertyId(), token)) {
            throw new ResourceNotFoundException("Property not found with given ID");
        }
        Optional<Design> existingDesign =designRepository.findByPropertyId(designRequest.getPropertyId());
        if(existingDesign.isPresent())
        {
            Design  design= existingDesign.get();
            Optional<Review> existingReview=reviewRepository.findByDesign_Id(design.getId());
            if(existingReview.isPresent()) {
                Review review = existingReview.get();
                if (review.getStatus() == ReviewStatus.PENDING)
                    throw new InvalidRequestException("No new design can be submitted with the given email and location for now try again after some time status is pending ");
                else if (review.getStatus() == ReviewStatus.APPROVED)
                    throw new InvalidRequestException("No new design can be submitted with the given email and location staus is approved");
                else if (review.getStatus() == ReviewStatus.REJECTED) {
                    design.setArea(designRequest.getArea());
                    review.setOfficerEmail(null);
                    review.setStatus(ReviewStatus.PENDING);
                    review.setComments(null);
                    review.setReviewedAt(null);
                    designRepository.save(design);
                    reviewRepository.save(review);
                    triggerKafka(design.getId(),design.getPropertyId());
                    return maptoDesignResponse(design, review);
                }
                else {
                    throw new InvalidRequestException("Unexpected review status");
                }
            }
            else {
                throw new ResourceNotFoundException("Review not found for existing design");
            }
        }
           Design newdesign=Design.builder()
                   .propertyId(designRequest.getPropertyId())
                   .userEmail(userEmail)
                   .area(designRequest.getArea())
                   .location(designRequest.getLocation()).build();
           Review newreview=Review.builder()
                   .design(newdesign)
                           .comments(null)
                                   .officerEmail(null)
                                           .reviewedAt(null)
                                                   .status(ReviewStatus.PENDING).build();
           designRepository.save(newdesign);
           reviewRepository.save(newreview);
           triggerKafka(newdesign.getId(),designRequest.getPropertyId());
           return maptoDesignResponse(newdesign,newreview);

    }

    public List<DesignResponse> getMyDesigns(String userEmail)
    {
        List<Design> designs=designRepository.findByUserEmail(userEmail);
        List<DesignResponse>designResponses = new ArrayList<>();
        for(Design design:designs)
        {
            Optional<Review> existingReview=reviewRepository.findByDesign_Id(design.getId());
            if(existingReview.isPresent())
            {
                Review review=existingReview.get();
                designResponses.add(maptoDesignResponse(design,review));
            }
            else
            {
                throw new ResourceNotFoundException("Review not found for one of the existing design");
            }
        }
        return designResponses;
    }

    @Transactional
    public DesignResponse updateDesign(DesignRequest designRequest, String userEmail, UUID designId)
    {
        Optional<Design> existingDesign=designRepository.findById(designId);
        if(existingDesign.isPresent())
        {
            Design design=existingDesign.get();
            if(design.getUserEmail().equalsIgnoreCase(userEmail))
            {
                Optional<Review> optionalReview=reviewRepository.findByDesign_Id(designId);
                if(optionalReview.isPresent())
                {
                    Review review=optionalReview.get();
                    if(review.getOfficerEmail()==null)
                    {
                            design.setLocation(designRequest.getLocation());
                            design.setArea(designRequest.getArea());
                            designRepository.save(design);
                            return maptoDesignResponse(design,review);
                    }
                    else
                        throw new InvalidRequestException("Cannot Update the design currently under Review");
                }
                else
                    throw new ResourceNotFoundException("Review not found for the given design id");
            }
            else
                throw new InvalidRequestException("Email associated with given design id not matches with the given email");

        }
        else
            throw new ResourceNotFoundException("Design not found with given Design id");
    }
    @Transactional
    public void deleteDesign(UUID designId, String userEmail)
    {
        Design design=designRepository.findById(designId).orElseThrow(()->new ResourceNotFoundException("No design found with given id"));
        if(design.getUserEmail().equalsIgnoreCase(userEmail))
        {
            designRepository.delete(design);
        }
        else
            throw new InvalidRequestException("User email not matches with email associated with given design id");
    }
    private DesignResponse maptoDesignResponse(Design design,Review review)
    {
        DesignResponse designResponse=new DesignResponse();
        designResponse.setPropertyId(design.getPropertyId());
        designResponse.setDesignId(design.getId());
        designResponse.setUserEmail(design.getUserEmail());
        designResponse.setLocation(design.getLocation());
        designResponse.setArea(design.getArea());
        designResponse.setCreatedAt(design.getCreatedAt());
        designResponse.setUpdatedAt(design.getUpdatedAt());
        designResponse.setComments(review.getComments());
        designResponse.setReviewedAt(review.getReviewedAt());
        designResponse.setStatus(review.getStatus());
        return designResponse;
    }
}
