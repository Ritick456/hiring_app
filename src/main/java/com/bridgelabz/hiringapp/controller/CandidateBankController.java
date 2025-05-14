package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.service.BankService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/candidates")
public class CandidateBankController {

    @Autowired
    private BankService bankService;

    @PutMapping("/{candidateId}/bank-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> addOrUpdateBankInfo(
            HttpServletRequest req,
            @PathVariable Long candidateId,
            @Valid @RequestBody CandidateBankInfoDto bankInfoDto) {

        CandidateBankInfo bankInfo = bankService.addOrUpdateBankInfo(candidateId, bankInfoDto);

        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(bankInfo)
                .message("Bank information saved successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(res);
    }
}
