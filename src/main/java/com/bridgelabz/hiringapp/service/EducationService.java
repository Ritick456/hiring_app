package com.bridgelabz.hiringapp.service;


import com.bridgelabz.hiringapp.dto.CandidateEducationDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateEducation;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import com.bridgelabz.hiringapp.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEducation addEducation(Long id , CandidateEducationDto dto){

        Optional<Candidate> candidate = candidateRepository.findById(id);
        Candidate candidate1 = candidate.get();
        CandidateEducation candidateEducation = new CandidateEducation();

        candidateEducation.setCandidate(candidate.get());
        candidateEducation.setInstitution(dto.getInstitution());
        candidateEducation.setDegree(dto.getDegree());
        candidateEducation.setYearOfPassing(dto.getYearOfPassing());

        candidate1.setEducation(candidateEducation);
        candidateRepository.save(candidate1);

        return educationRepository.save(candidateEducation);
    }
}
