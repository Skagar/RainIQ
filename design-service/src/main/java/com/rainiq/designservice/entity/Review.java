package com.rainiq.designservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id ;

    @Column(name = "officer_email")
    private String officerEmail;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;

    @Column
    private String comments;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @OneToOne
    @JoinColumn(name = "design_id", referencedColumnName = "id", unique = true, nullable = false)
    private Design design;

}
