package com.bridgelabz.hiringapp.repository;


import com.bridgelabz.hiringapp.entity.CandidatePersonalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepository extends JpaRepository<CandidatePersonalInfo , Long> {



}
