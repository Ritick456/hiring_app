package com.bridgelabz.hiringapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateEducationDto {

    private Long candidateId;

    private String degree;

    private String institution;

    private Integer yearOfPassing;
}
