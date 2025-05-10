package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.*;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.service.CandidateService;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiSuccessResponseDto> createCandidate(HttpServletRequest req, @RequestBody CandidateDto candidateDto){

        Candidate candidate = candidateService.createCandidate(candidateDto);

        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .path(req.getRequestURI())
                .data(candidate)
                .message("User Created")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(res);

    }

    @GetMapping("/getall")
    public ResponseEntity<ApiSuccessResponseDto> getAllCandidate(HttpServletRequest req){

        List<Candidate> list = candidateService.getAllCandidate();

        ApiSuccessResponseDto response = ApiSuccessResponseDto.builder()
                .message("Candidates fetched successfully")
                .data(list)
                .path(req.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApiSuccessResponseDto> getCandidateById(HttpServletRequest req, @PathVariable Long id){

        Optional<Candidate> optionalCandidate = candidateService.getCandidateById(id);

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Candidate fetch successfully");

    }

    @GetMapping("getcount")
    public ResponseEntity<ApiSuccessResponseDto> getNumberOfCandidates(HttpServletRequest req){

        Integer count = candidateService.getCount();

        return ResponseBuilder.success( count ,  req.getRequestURI() , "Fetching Counts");

    }

    @GetMapping("/gethired")
    public ResponseEntity<ApiSuccessResponseDto> getHiredCandidates(HttpServletRequest req){

        List<Candidate> list = candidateService.getHiredCandidate(Candidate.CandidateStatus.ONBOARDED);

        return  ResponseBuilder.success( list , req.getRequestURI() , "Hired Candiates fetched Successfully" );

    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApiSuccessResponseDto> updateStatus(HttpServletRequest req, @PathVariable Long id , @RequestBody CandidateStatusDto candidateStatusDto){

        Optional<Candidate> optionalCandidate = Optional.ofNullable(candidateService.updateStatus(id , candidateStatusDto));

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Updated Candidate successfully");
    }

    @PutMapping("/onboardstatus/{id}")
    public ResponseEntity<ApiSuccessResponseDto> updateOnboardStatus(HttpServletRequest req, @PathVariable Long id , @RequestBody CandidateOnboardedStatusDto candidateOnboardedStatusDto){

        Optional<Candidate> optionalCandidate = Optional.ofNullable(candidateService.updateOnboardStatus(id , candidateOnboardedStatusDto));

        Candidate candidate = optionalCandidate.get();

        return ResponseBuilder.success(candidate , req.getRequestURI() , "Updated Onboaredstatus successfully");

    }






}
