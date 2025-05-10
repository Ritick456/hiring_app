package com.bridgelabz.hiringapp.entity;

import com.bridgelabz.hiringapp.entity.Candidate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateBankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @OneToOne(mappedBy = "candidateBankInfo")
    private Candidate candidate;

    private String bankName;

    private String accountNumber;

    private String ifscCode;
}
