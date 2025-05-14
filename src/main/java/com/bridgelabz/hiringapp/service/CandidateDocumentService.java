package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateDocument;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.exception.InvalidStatusException;
import com.bridgelabz.hiringapp.repository.CandidateDocumentRepository;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class CandidateDocumentService {

    @Autowired
    private CandidateDocumentRepository candidateDocumentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private static final String UPLOAD_DIR = "uploads/documents";

    public CandidateDocument uploadDocument(Long id, MultipartFile file, String documentType) throws IOException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + id));

        // Create upload directory if not exists
        String projectDir = System.getProperty("user.dir");
        String uploadPath = Paths.get(projectDir, UPLOAD_DIR).toString();
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Sanitize and create a unique filename
        String originalFileName = file.getOriginalFilename();
        String sanitizedFileName = originalFileName != null
                ? originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_")
                : "document";

        String fileName = UUID.randomUUID() + "_" + sanitizedFileName;
        String filePath = Paths.get(uploadPath, fileName).toString();

        // Save file to disk
        File dest = new File(filePath);
        file.transferTo(dest);

        // Save metadata in database
        CandidateDocument document = new CandidateDocument();

        document.setCandidate(candidate);
        document.setDocumentType(documentType);
        document.setFileUrl(filePath);
        document.setVerified(false);

        return candidateDocumentRepository.save(document);
    }

    /**
     * Verifies a specific document of a candidate.
     *
     * @param candidateId         Candidate ID
     * @param candidateDocumentId Document ID to verify
     * @return Updated CandidateDocument
     */
    public CandidateDocument verifyCandidateDocument(Long candidateId, Long candidateDocumentId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + candidateId));

        CandidateDocument document = candidateDocumentRepository.findById(candidateDocumentId)
                .orElseThrow(() -> new InvalidStatusException("Document not found with ID: " + candidateDocumentId));

        if (!document.getCandidate().getId().equals(candidate.getId())) {
            throw new InvalidStatusException("Document does not belong to the specified candidate.");
        }

        document.setVerified(true);
        return candidateDocumentRepository.save(document);
    }
}
