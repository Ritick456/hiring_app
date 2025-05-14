package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.CandidateEducationDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateEducation;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import com.bridgelabz.hiringapp.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EducationService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private EducationRepository educationRepository;

    public CandidateEducation addOrUpdateEducation(Long candidateId, CandidateEducationDto dto) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + candidateId));

        CandidateEducation education = Optional.ofNullable(candidate.getEducation())
                .orElse(new CandidateEducation());

        education.setCandidate(candidate);
        education.setDegree(dto.getDegree());
        education.setInstitution(dto.getInstitution());
        education.setYearOfPassing(dto.getYearOfPassing());

        candidate.setEducation(education);
        candidateRepository.save(candidate);

        return educationRepository.save(education);
    }
}
