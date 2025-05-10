package com.bridgelabz.hiringapp.repository;

import com.bridgelabz.hiringapp.entity.CandidateEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EducationRepository extends JpaRepository<CandidateEducation , Long> {
}
