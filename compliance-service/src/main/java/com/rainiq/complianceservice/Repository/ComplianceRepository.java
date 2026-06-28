package com.rainiq.complianceservice.Repository;

import com.rainiq.complianceservice.entity.ComplianceRecord;
import com.rainiq.complianceservice.entity.ComplianceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord,UUID> {
    Optional<ComplianceRecord> findByDesignId(UUID designId);
    List<ComplianceRecord> findByComplianceStatus(ComplianceStatus complianceStatus);
}
