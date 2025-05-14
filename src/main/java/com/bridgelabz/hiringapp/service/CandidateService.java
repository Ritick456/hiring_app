package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.CandidateDto;
import com.bridgelabz.hiringapp.dto.CandidateOnboardedStatusDto;
import com.bridgelabz.hiringapp.dto.CandidateStatusDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.exception.InvalidStatusException;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    public List<Candidate> getAllCandidate(int page , int size) {

        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("id").descending());
        return candidateRepository.findAll((Sort) pageable);
    }

    public Optional<Candidate> getCandidateById(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isEmpty()) {
            throw new CandidateNotFoundException("Candidate with ID " + id + " not found.");
        }
        return candidate;
    }

    public Integer getCount() {
        return candidateRepository.findAll().size();
    }

    public List<Candidate> getHiredCandidate(Candidate.CandidateStatus status) {
        return candidateRepository.findByStatus(status);
    }

    public Candidate updateStatus(Long id, CandidateStatusDto candidateStatusDto) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isEmpty()) {
            throw new CandidateNotFoundException("Candidate with ID " + id + " not found.");
        }

        if (candidateStatusDto.getStatus() == null) {
            throw new InvalidStatusException("Status cannot be null.");
        }

        Candidate candidate1 = candidate.get();
        candidate1.setStatus(candidateStatusDto.getStatus());
        return candidateRepository.save(candidate1);
    }

    public Candidate updateOnboardStatus(Long id, CandidateOnboardedStatusDto candidateOnboardedStatusDto) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        if (optionalCandidate.isEmpty()) {
            throw new CandidateNotFoundException("Candidate with ID " + id + " not found.");
        }

        if (candidateOnboardedStatusDto.getOnboardStatus() == null) {
            throw new InvalidStatusException("Onboard status cannot be null.");
        }

        Candidate candidate = optionalCandidate.get();
        candidate.setOnboardStatus(candidateOnboardedStatusDto.getOnboardStatus());
        return candidateRepository.save(candidate);
    }

    public String deleteCandidate(Long id){

        candidateRepository.deleteById(id);

        return "Candidate Deleted Succesfully";

    }
}
