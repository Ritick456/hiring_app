package com.bridgelabz.hiringapp.controller;


import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class CandidateBankController {


    @Autowired
    private BankService bankService;

    @PutMapping("/{id}/bank-info")
    public ResponseEntity<ApiSuccessResponseDto> putBankInfo(HttpServletRequest req , @PathVariable Long id , @RequestBody CandidateBankInfoDto candidateBankInfoDto){

        CandidateBankInfo candidateBankInfo = bankService.putBankInfo(id , candidateBankInfoDto);

        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(candidateBankInfo)
                .message("BankInfo added successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(res);

    }


}
