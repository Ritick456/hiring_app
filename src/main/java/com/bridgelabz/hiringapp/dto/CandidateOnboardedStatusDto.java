package com.bridgelabz.hiringapp.dto;


import com.bridgelabz.hiringapp.entity.Candidate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateOnboardedStatusDto {

    private Candidate.OnboardStatus onboardStatus;


}
