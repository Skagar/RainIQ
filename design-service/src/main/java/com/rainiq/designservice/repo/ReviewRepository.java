package com.rainiq.designservice.repo;

import com.rainiq.designservice.entity.Review;
import com.rainiq.designservice.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByOfficerEmail(String email);
    List<Review> findByStatus(ReviewStatus reviewStatus);
    Optional<Review> findByDesign_Id(UUID designId);
}
