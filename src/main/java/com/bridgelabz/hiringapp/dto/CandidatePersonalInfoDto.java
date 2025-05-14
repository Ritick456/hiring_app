package com.bridgelabz.hiringapp.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidatePersonalInfoDto {

    private String dob;

    private String address;

    private String nationality;
}
