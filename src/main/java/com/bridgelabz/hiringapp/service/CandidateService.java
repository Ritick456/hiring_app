package com.bridgelabz.hiringapp.service;


import com.bridgelabz.hiringapp.dto.CandidateDto;
import com.bridgelabz.hiringapp.dto.CandidateOnboardedStatusDto;
import com.bridgelabz.hiringapp.dto.CandidateStatusDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;


    public Candidate createCandidate(CandidateDto candidateDto) {

        Candidate candidate = new Candidate(candidateDto);

        return candidateRepository.save(candidate);

    }

    public List<Candidate> getAllCandidate(){

        return candidateRepository.findAll();

    }

    public Optional<Candidate> getCandidateById(Long id){
        return candidateRepository.findById(id);
    }

    public Integer getCount(){

        return candidateRepository.findAll().size();

    }

    public List<Candidate> getHiredCandidate(Candidate.CandidateStatus status){

        return candidateRepository.findByStatus(status);

    }

    public Candidate updateStatus(Long id , CandidateStatusDto candidateStatusDto){

        Optional<Candidate> candidate = candidateRepository.findById(id);
        Candidate candidate1 = candidate.get();

        candidate1.setStatus(candidateStatusDto.getStatus());

        return candidateRepository.save(candidate1);

    }

    public Candidate updateOnboardStatus(Long id, CandidateOnboardedStatusDto candidateOnboardedStatusDto){

        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        Candidate candidate = optionalCandidate.get();

        candidate.setOnboardStatus(candidateOnboardedStatusDto.getOnboardStatus());

        return candidateRepository.save(candidate);
    }

}

