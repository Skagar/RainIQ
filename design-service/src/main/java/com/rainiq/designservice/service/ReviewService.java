package com.rainiq.designservice.service;

import com.rainiq.designservice.dto.ReviewRequest;
import com.rainiq.designservice.dto.ReviewResponse;
import com.rainiq.designservice.entity.Design;
import com.rainiq.designservice.entity.Review;
import com.rainiq.designservice.entity.ReviewStatus;
import com.rainiq.designservice.exception.InvalidRequestException;
import com.rainiq.designservice.repo.DesignRepository;
import com.rainiq.designservice.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewResponse> getAllPendingReviews()
    {
        List<Review> reviewList=reviewRepository.findByStatus(ReviewStatus.PENDING);
        List<ReviewResponse>reviewResponses=new ArrayList<>();
        for(Review review:reviewList)
        {
                reviewResponses.add(maptoReviewResponse(review));
        }
        return reviewResponses;
    }

    @Transactional
    public ReviewResponse updateReviewByDesignId(UUID designId, ReviewRequest reviewRequest,String email)
    {
            Review review=reviewRepository.findByDesign_Id(designId).orElseThrow(()->new InvalidRequestException("No review exist for the given design  id"));
            if(review.getOfficerEmail()==null)
            {
                review.setComments(reviewRequest.getComment());
                review.setStatus(reviewRequest.getStatus());
                review.setOfficerEmail(email);
                review.setReviewedAt(LocalDateTime.now());
                reviewRepository.save(review);
                return maptoReviewResponse(review);
            }
            else
                throw new InvalidRequestException("Review already exists for the given design id");
    }
    private ReviewResponse maptoReviewResponse(Review review)
    {
        ReviewResponse reviewResponse=new ReviewResponse();
        reviewResponse.setDesignId(review.getDesign().getId());
        reviewResponse.setReviewId(review.getId());
        reviewResponse.setUserEmail(review.getDesign().getUserEmail());
        reviewResponse.setOfficerEmail(review.getOfficerEmail());
        reviewResponse.setArea(review.getDesign().getArea());
        reviewResponse.setLocation(review.getDesign().getLocation());
        reviewResponse.setCreatedAt(review.getDesign().getCreatedAt());
        reviewResponse.setUpdatedAt(review.getDesign().getUpdatedAt());
        reviewResponse.setStatus(review.getStatus());
        reviewResponse.setReviewedAt(review.getReviewedAt());
        reviewResponse.setComments(review.getComments());
      return reviewResponse;
    }
}
