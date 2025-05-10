package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.CandidateDocument;
import com.bridgelabz.hiringapp.exception.CandidateNotFoundException;
import com.bridgelabz.hiringapp.repository.CandidateDocumentRepository;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class CandidateDocumentService {

    @Autowired
    private CandidateDocumentRepository candidateDocumentRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private static final String UPLOAD_DIR = "uploads/documents";

    public CandidateDocument uploadDocument(Long id, MultipartFile file, String documentType) throws IOException {

        // Fetch candidate by id
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + id));

        // Get absolute path from project root
        String projectDir = System.getProperty("user.dir");
        String uploadPath = Paths.get(projectDir, UPLOAD_DIR).toString();

        // Ensure the upload directory exists
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Sanitize file name
        String originalFileName = file.getOriginalFilename();
        String sanitizedFileName = originalFileName != null
                ? originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_")
                : "document";

        // Generate unique file name
        String fileName = UUID.randomUUID() + "_" + sanitizedFileName;
        String filePath = Paths.get(uploadPath, fileName).toString();

        // Save the file
        File dest = new File(filePath);
        file.transferTo(dest);

        // Create document entry
        CandidateDocument document = new CandidateDocument();
        document.setCandidate(candidate);
        document.setDocumentType(documentType);
        document.setFileUrl(filePath);  // You can also store just the relative path if needed
        document.setVerified(false);

        return candidateDocumentRepository.save(document);
    }



    public CandidateDocument verifyCandidateDocument(Long candidateId , Long candidateDocumentId){

        Optional<Candidate> candidate  = candidateRepository.findById(candidateId);

        Optional<CandidateDocument> candidateDocument = candidateDocumentRepository.findById(candidateDocumentId);

        Candidate candidate1 = candidate.get();

        CandidateDocument document = candidateDocument.get();

//        if(!document.getCandidate().getId().equals(candidateId)){
//            throw new RuntimeException("Document does not belong to the specified candidate.");
//        }

        document.setVerified(true);

        return candidateDocumentRepository.save(document);
    }


}
