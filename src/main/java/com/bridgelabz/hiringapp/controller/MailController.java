package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.EmailRequestDto;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import com.bridgelabz.hiringapp.service.JobOfferNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidates")
public class MailController {

    @Autowired
    private JobOfferNotificationService notificationService;

    @PostMapping("/job-offer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendJobOfferMail(@RequestBody EmailRequestDto emailDto, HttpServletRequest request) {
        try {
            notificationService.sendJobOfferEmail(emailDto);
            return ResponseBuilder.success(null, request.getRequestURI(), "Email sent and notification recorded.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
