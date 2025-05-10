package com.bridgelabz.hiringapp.dto;
import com.bridgelabz.hiringapp.entity.Candidate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
public class CandidateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Candidate.CandidateStatus status;
    private Candidate.OnboardStatus onboardStatus;

}
