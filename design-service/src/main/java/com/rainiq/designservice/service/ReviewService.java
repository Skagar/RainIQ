package com.rainiq.designservice.service;

import com.rainiq.designservice.repo.DesignRepository;
import com.rainiq.designservice.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final DesignRepository designRepository;
    private final ReviewRepository reviewRepository;

    
}
