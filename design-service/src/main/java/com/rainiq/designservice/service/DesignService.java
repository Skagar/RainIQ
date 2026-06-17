package com.rainiq.designservice.service;

import com.rainiq.designservice.dto.DesignRequest;
import com.rainiq.designservice.dto.DesignResponse;
import com.rainiq.designservice.repo.DesignRepository;
import com.rainiq.designservice.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignService {
    private final DesignRepository designRepository;
    private final ReviewRepository reviewRepository;

    public DesignResponse submitDesign(DesignRequest designRequest, String userEmail)
    {

    }
}
