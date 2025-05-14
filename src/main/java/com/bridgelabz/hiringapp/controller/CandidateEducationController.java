package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.dto.CandidateEducationDto;
import com.bridgelabz.hiringapp.entity.CandidateEducation;
import com.bridgelabz.hiringapp.service.EducationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/candidates")
public class CandidateEducationController {

    @Autowired
    private EducationService educationService;

    @PutMapping("/{candidateId}/education-info")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> addOrUpdateEducation(
            HttpServletRequest request,
            @PathVariable Long candidateId,
            @RequestBody CandidateEducationDto educationDto) {

        CandidateEducation education = educationService.addOrUpdateEducation(candidateId, educationDto);

        ApiSuccessResponseDto response = ApiSuccessResponseDto.builder()
                .path(request.getRequestURI())
                .message("Education info updated successfully")
                .timestamp(LocalDateTime.now())
                .data(education)
                .build();

        return ResponseEntity.ok(response);
    }
}
