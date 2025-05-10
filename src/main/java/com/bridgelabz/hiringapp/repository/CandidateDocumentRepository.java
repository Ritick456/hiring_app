package com.bridgelabz.hiringapp.repository;

import com.bridgelabz.hiringapp.entity.CandidateDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CandidateDocumentRepository extends JpaRepository<CandidateDocument , Long> {
}
