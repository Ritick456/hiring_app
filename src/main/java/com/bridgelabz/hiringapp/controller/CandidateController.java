package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.*;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.service.CandidateService;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> createCandidate(HttpServletRequest req, @RequestBody @Valid CandidateDto candidateDto){

        Candidate candidate = candidateService.createCandidate(candidateDto);

        ApiSuccessResponseDto response = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(candidate)
                .message("User Created")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseBuilder.success(response , req.getRequestURI(), "created user");

    }

    @GetMapping("/getall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiSuccessResponseDto> getAllCandidate(  @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,  HttpServletRequest req){

        Page<Candidate> candidatePage = (Page<Candidate>) candidateService.getAllCandidate(page , size);

        ApiSuccessResponseDto response = ApiSuccessResponseDto.builder()
                .message("Candidates fetched successfully")
                .data(candidatePage.getContent())
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseBuilder.success(response , req.getRequestURI(), "fetching all candidates");
    }

    @GetMapping("/getbyid/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> getCandidateById(HttpServletRequest req, @PathVariable Long id){

        Optional<Candidate> optionalCandidate = candidateService.getCandidateById(id);

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Candidate fetch successfully");

    }

    @GetMapping("getcount")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiSuccessResponseDto> getNumberOfCandidates(HttpServletRequest req){

        Integer count = candidateService.getCount();

        return ResponseBuilder.success( count ,  req.getRequestURI() , "Fetching Counts");

    }

    @GetMapping("/gethired")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> getHiredCandidates(HttpServletRequest req){

        List<Candidate> list = candidateService.getHiredCandidate(Candidate.CandidateStatus.ONBOARDED);

        return  ResponseBuilder.success( list , req.getRequestURI() , "Hired Candiates fetched Successfully" );

    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")

    public ResponseEntity<ApiSuccessResponseDto> updateStatus(HttpServletRequest req, @PathVariable Long id , @RequestBody CandidateStatusDto candidateStatusDto){

        Optional<Candidate> optionalCandidate = Optional.ofNullable(candidateService.updateStatus(id , candidateStatusDto));

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Updated Candidate successfully");
    }

    @PutMapping("/onboardstatus/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")

    public ResponseEntity<ApiSuccessResponseDto> updateOnboardStatus(HttpServletRequest req, @PathVariable Long id , @RequestBody CandidateOnboardedStatusDto candidateOnboardedStatusDto){

        Optional<Candidate> optionalCandidate = Optional.ofNullable(candidateService.updateOnboardStatus(id , candidateOnboardedStatusDto));

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Updated Onboaredstatus successfully");

    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCandidate(@PathVariable Long id){

        return candidateService.deleteCandidate(id);

    }






}
