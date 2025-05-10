package com.bridgelabz.hiringapp.repository;

import com.bridgelabz.hiringapp.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate , Long> {

    List<Candidate> findByStatus(Candidate.CandidateStatus status);

    Optional<Object> findByEmail(String to);
}
