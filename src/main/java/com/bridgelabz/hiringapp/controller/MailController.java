package com.bridgelabz.hiringapp.controller;

import com.bridgelabz.hiringapp.dto.EmailRequestDto;
import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import com.bridgelabz.hiringapp.service.JobOfferNotificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    @Autowired
    private JobOfferNotificationService notificationService;

    @PostMapping("/job-offer")
    public ResponseEntity<?> sendJobOfferMail(@RequestBody EmailRequestDto emailDto, HttpServletRequest request) {
        try {
            notificationService.sendJobOfferEmail(emailDto);
            return ResponseBuilder.success(null, request.getRequestURI(), "Email sent and notification recorded.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
