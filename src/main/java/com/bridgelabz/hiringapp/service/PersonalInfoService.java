package com.bridgelabz.hiringapp.service;

//import com.bridgelabz.hiringapp.dto.CandidateBankInfoDto;
import com.bridgelabz.hiringapp.dto.CandidatePersonalInfoDto;
import com.bridgelabz.hiringapp.entity.Candidate;
//import com.bridgelabz.hiringapp.entity.CandidateBankInfo;
import com.bridgelabz.hiringapp.entity.CandidatePersonalInfo;
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

        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        Candidate candidate = optionalCandidate.get();

        CandidatePersonalInfo candidatePersonalInfo = new CandidatePersonalInfo();

        candidatePersonalInfo.setAddress(candidatePersonalInfoDto.getAddress());
        candidatePersonalInfo.setDob(candidatePersonalInfoDto.getDob());
        candidatePersonalInfo.setGender(candidatePersonalInfoDto.getGender());
        candidatePersonalInfo.setNationality(candidatePersonalInfoDto.getNationality());

        candidatePersonalInfo.setCandidate(candidate);

        candidate.setCandidatePersonalInfo(candidatePersonalInfo);
        personalInfoRepository.save(candidatePersonalInfo);

        candidateRepository.save(candidate);

        return candidatePersonalInfo ;

    }

}
