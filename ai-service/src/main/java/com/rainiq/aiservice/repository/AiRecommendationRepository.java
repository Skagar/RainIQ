package com.rainiq.aiservice.repository;

import com.rainiq.aiservice.entity.AiRecommendation;
import com.rainiq.aiservice.entity.AiResponseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AiRecommendationRepository extends JpaRepository<AiRecommendation,UUID> {
    List<AiRecommendation> findByStatus(AiResponseStatus status);

    Optional<AiRecommendation> findByDesignId(UUID designId);
}
