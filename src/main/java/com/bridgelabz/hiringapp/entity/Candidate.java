package com.bridgelabz.hiringapp.entity;


import com.bridgelabz.hiringapp.dto.CandidateDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Candidate {

    public enum CandidateStatus {
        APPLIED,
        INTERVIEWED,
        OFFERED,
        ONBOARDED
    }

    public enum OnboardStatus {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CandidateStatus status;
    private OnboardStatus onboardStatus;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "education_id")
    private CandidateEducation education;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_id")
    private CandidateBankInfo candidateBankInfo;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_id")
    private CandidatePersonalInfo candidatePersonalInfo;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CandidateDocument> candidateDocument;

    @OneToMany(mappedBy = "candidate" , cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<JobOfferNotification> jobOfferNotificationList;





   public Candidate(CandidateDto candidateDto){
        this.firstName = candidateDto.getFirstName();
        this.lastName = candidateDto.getLastName();
        this.email = candidateDto.getEmail();
        this.phoneNumber = candidateDto.getPhoneNumber();
        this.createdAt = candidateDto.getCreatedAt();
        this.updatedAt = candidateDto.getUpdatedAt();
        this.status = candidateDto.getStatus();
        this.onboardStatus = candidateDto.getOnboardStatus();
    }


}
