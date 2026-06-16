package com.rainiq.designservice.repo;

import com.rainiq.designservice.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DesignRepository extends JpaRepository<Design, UUID> {
    List<Design> findByUserEmail(String email);
    List<Design> findByLocation(String location);
    Optional<Design> findByUserEmailAndLocation(String email,String location);
}
