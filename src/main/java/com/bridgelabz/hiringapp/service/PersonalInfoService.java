package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.CandidatePersonalInfoDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidatePersonalInfo;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import com.bridgelabz.hiringapp.repository.PersonalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalInfoService {

    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidatePersonalInfo putPersonalInfo(Long id , CandidatePersonalInfoDto candidatePersonalInfoDto){

        // Check if the candidate exists, if not throw an exception
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + id));

        // Create and set up the CandidatePersonalInfo object
        CandidatePersonalInfo candidatePersonalInfo = new CandidatePersonalInfo();
        candidatePersonalInfo.setAddress(candidatePersonalInfoDto.getAddress());
        candidatePersonalInfo.setDob(candidatePersonalInfoDto.getDob());

        candidatePersonalInfo.setNationality(candidatePersonalInfoDto.getNationality());

        candidatePersonalInfo.setCandidate(candidate);

        // Set candidate's personal info and save
        candidate.setCandidatePersonalInfo(candidatePersonalInfo);
        personalInfoRepository.save(candidatePersonalInfo);

        // Save the candidate with updated personal info
        candidateRepository.save(candidate);

        return candidatePersonalInfo;
    }
}
