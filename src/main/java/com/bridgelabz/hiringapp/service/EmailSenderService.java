package com.bridgelabz.hiringapp.service;

import com.bridgelabz.hiringapp.dto.EmailRequestDto;
import com.bridgelabz.hiringapp.entity.JobOfferNotification;
//import com.bridgelabz.hiringapp.model.JobOfferNotification;
import com.bridgelabz.hiringapp.repository.JobOfferNotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JobOfferNotificationRepository notificationRepo;

    @RabbitListener(queues = "job.offer.notification.queue")
    public void sendEmail(EmailRequestDto request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());

            mailSender.send(message);

            // update status in DB
            JobOfferNotification notification = notificationRepo.findById(request.getNotificationId())
                    .orElseThrow();
            notification.setSent(true);
            notification.setSentAt(LocalDateTime.now());
            notificationRepo.save(notification);

        } catch (Exception e) {
            JobOfferNotification notification = notificationRepo.findById(request.getNotificationId())
                    .orElseThrow();
            notification.setRetryCount(notification.getRetryCount() + 1);
            notification.setErrorMessage(e.getMessage());
            notificationRepo.save(notification);
        }
    }


    @RabbitListener(queues = "otp.notification.queue")
    public void otpEmail(EmailRequestDto request) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.getTo());
            message.setSubject(request.getSubject());
            message.setText(request.getBody());

            mailSender.send(message);



    }
}
