package com.bridgelabz.hiringapp.controller;


import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import com.bridgelabz.hiringapp.entity.CandidateDocument;
import com.bridgelabz.hiringapp.service.CandidateDocumentService;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/candidates")
public class CandidateDocumentController {

    @Autowired
    private CandidateDocumentService candidateDocumentService;

    // Endpoint for uploading a document
    @PostMapping("/{id}/upload-document")
    public ResponseEntity<ApiSuccessResponseDto> uploadDocument(
            HttpServletRequest request,
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType) {

        try {
            // Call the service to upload the document
            CandidateDocument document = candidateDocumentService.uploadDocument(id, file, documentType);

            // Prepare the success response
            ApiSuccessResponseDto responseDto = ApiSuccessResponseDto.builder()
                    .path(request.getRequestURI())
                    .data(document)
                    .message("Document uploaded successfully")
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            // Handle exceptions here (e.g., file upload failures, candidate not found, etc.)
            ApiSuccessResponseDto errorResponse = ApiSuccessResponseDto.builder()
                    .path(request.getRequestURI())
                    .message("Error uploading document: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .build();

            return ResponseEntity.status(500).body(errorResponse);  // Return internal server error status
        }
    }


    @PutMapping("/{id}/verify-document/{documentid}")
    public ResponseEntity<ApiSuccessResponseDto> verifyDocument(HttpServletRequest req, @PathVariable Long id , @PathVariable Long documentid){

        CandidateDocument candidateDocument = candidateDocumentService.verifyCandidateDocument(id , documentid);

        return ResponseBuilder.success( candidateDocument , req.getRequestURI() , "Verify Successfully" );

    }
}

