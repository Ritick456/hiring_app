package com.bridgelabz.hiringapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonBackReference
    private Candidate candidate;

    @Column(name = "document_type", nullable = false)
    private String documentType;  // e.g., Resume, ID Proof

    @Column(name = "file_url", columnDefinition = "TEXT", nullable = false)
    private String fileUrl;  // path or URL to the uploaded file

    @Column(nullable = false)
    private Boolean verified = false;  // defaults to not verified
}
