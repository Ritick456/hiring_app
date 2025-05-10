package com.bridgelabz.hiringapp.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidatePersonalInfoDto {

    private Long candidateId; // used to link to Candidate

    private LocalDate dob;

    private String gender;

    private String address;

    private String nationality;
}
