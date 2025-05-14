package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.entity.CandidateDocument;
import com.bridgelabz.hiringapp.service.CandidateDocumentService;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/candidates")
public class CandidateDocumentController {

    @Autowired
    private CandidateDocumentService candidateDocumentService;

    @PostMapping("/{id}/upload-document")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiSuccessResponseDto> uploadDocument(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType) throws IOException {

        CandidateDocument document = candidateDocumentService.uploadDocument(id, file, documentType);

        ApiSuccessResponseDto responseDto = ApiSuccessResponseDto.builder()
                .path(request.getRequestURI())
                .data(document)
                .message("Document uploaded successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}/verify-document/{documentid}")
    public ResponseEntity<ApiSuccessResponseDto> verifyDocument(
            HttpServletRequest req,
            @PathVariable Long id,
            @PathVariable Long documentid) {

        CandidateDocument candidateDocument = candidateDocumentService.verifyCandidateDocument(id, documentid);

        return ResponseBuilder.success(candidateDocument, req.getRequestURI(), "Verify Successfully");
    }
}
