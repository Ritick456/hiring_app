package com.bridgelabz.hiringapp.service;


import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.repository.BankRepository;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
//
//@Service
//public class BankService {
//
//    @Autowired
//    private BankRepository bankRepository;
//
//    @Autowired
//    private CandidateRepository candidateRepository;
//
//
//    public CandidateBankInfo putBankInfo(Long id , CandidateBankInfoDto candidateBankInfoDto){
//
//        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
//
//        Candidate candidate = optionalCandidate.get();
//
//        CandidateBankInfo candidateBankInfo = new CandidateBankInfo();
//
//        candidateBankInfo.setBankName(candidateBankInfoDto.getBankName());
//        candidateBankInfo.setCandidate(candidate);
//        candidateBankInfo.setAccountNumber(candidateBankInfoDto.getAccountNumber());
//        candidateBankInfo.setIfscCode(candidateBankInfoDto.getIfscCode());
//
//        candidate.setCandidateBankInfo(candidateBankInfo);
//
//        candidateRepository.save(candidate);
//
//        return bankRepository.save(candidateBankInfo);
//
//    }
//
//}


import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateBankInfo putBankInfo(Long id, CandidateBankInfoDto candidateBankInfoDto) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate with ID " + id + " not found."));

        CandidateBankInfo candidateBankInfo = new CandidateBankInfo();
        candidateBankInfo.setBankName(candidateBankInfoDto.getBankName());
        candidateBankInfo.setCandidate(candidate);
        candidateBankInfo.setAccountNumber(candidateBankInfoDto.getAccountNumber());
        candidateBankInfo.setIfscCode(candidateBankInfoDto.getIfscCode());

        candidate.setCandidateBankInfo(candidateBankInfo);
        candidateRepository.save(candidate);

        return bankRepository.save(candidateBankInfo);
    }
}

