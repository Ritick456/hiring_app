package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.repository.BankRepository;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateBankInfo addOrUpdateBankInfo(Long candidateId, CandidateBankInfoDto dto) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with ID " + candidateId + " not found."));

        CandidateBankInfo bankInfo = Optional.ofNullable(candidate.getCandidateBankInfo())
                .orElse(new CandidateBankInfo());

        bankInfo.setBankName(dto.getBankName());
        bankInfo.setAccountNumber(dto.getAccountNumber());
        bankInfo.setIfscCode(dto.getIfscCode());
        bankInfo.setCandidate(candidate);

        candidate.setCandidateBankInfo(bankInfo);
        candidateRepository.save(candidate);

        return bankRepository.save(bankInfo);
    }
}
