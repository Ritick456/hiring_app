package com.bridgelabz.hiringapp.controller;


import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.dto.CandidatePersonalInfoDto;
import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.entity.CandidatePersonalInfo;
import com.bridgelabz.hiringapp.service.PersonalInfoService;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/candidates")
public class CandidatePersonalInfoController {

    @Autowired
    private PersonalInfoService personalInfoService;


    @PutMapping("/{id}/personal-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> putBankInfo(HttpServletRequest req , @PathVariable Long id , @RequestBody CandidatePersonalInfoDto candidatePersonalInfoDto){

        CandidatePersonalInfo candidatePersonalInfo = personalInfoService.putPersonalInfo(id , candidatePersonalInfoDto);

        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(candidatePersonalInfo)
                .message("Personal added successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseBuilder.success(res , req.getRequestURI(), "Updated Candidate Personal Info");

    }


}
