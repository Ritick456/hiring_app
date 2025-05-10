package com.bridgelabz.hiringapp.controller;


import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.dto.CandidateEducationDto;
import com.bridgelabz.hiringapp.entity.CandidateEducation;
import com.bridgelabz.hiringapp.repository.EducationRepository;
import com.bridgelabz.hiringapp.service.EducationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/education")
public class CandidateEducationController {

    @Autowired
    private EducationService educationService;

    @PutMapping("/{id}/education-info")
    public ResponseEntity<ApiSuccessResponseDto> createEducation(HttpServletRequest req , @PathVariable  Long id , @RequestBody CandidateEducationDto candidateEducationDto){

        CandidateEducation candidateEducation  = educationService.addEducation(id , candidateEducationDto);

        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(candidateEducation)
                .message("Education added successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(res);

    }

}
