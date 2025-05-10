package com.bridgelabz.hiringapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateBankInfoDto {

    private Long candidateId;

    private String bankName;

    private String accountNumber;

    private String ifscCode;
}
