package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.EmailRequestDto;
import com.bridgelabz.hiringapp.entity.Candidate;
import com.bridgelabz.hiringapp.entity.JobOfferNotification;
import com.bridgelabz.hiringapp.repository.CandidateRepository;
import com.bridgelabz.hiringapp.repository.JobOfferNotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobOfferNotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private JobOfferNotificationRepository notificationRepo;

    // New method - handles full EmailRequestDto input from controller
    public void sendJobOfferEmail(EmailRequestDto emailDto) {
        Candidate candidate = (Candidate) candidateRepo.findByEmail(emailDto.getTo())
                .orElseThrow(() -> new RuntimeException("Candidate with email " + emailDto.getTo() + " not found"));

        JobOfferNotification notification = JobOfferNotification.builder()
                .candidate(candidate)
                .sent(false)
                .retryCount(0)
                .build();

        notificationRepo.save(notification);

        // Attach notification ID to DTO before sending
        emailDto.setNotificationId(notification.getId());

        rabbitTemplate.convertAndSend("job.offer.notification.exchange", "job.offer.notification", emailDto);
    }
}
