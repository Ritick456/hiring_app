package com.bridgelabz.hiringapp.service;


import com.bridgelabz.hiringapp.dto.*;
import com.bridgelabz.hiringapp.entity.User;
import com.bridgelabz.hiringapp.exception.UserNotFoundException;
import com.bridgelabz.hiringapp.repository.UserRepository;
import com.bridgelabz.hiringapp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class  AuthService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    @Autowired
    private final AuthenticationManager authManager;

    @Autowired
    private EmailSenderService emailSenderService;


    private Map<String , String> otpStore = new HashMap<>();

    public String register(RegisterDto request) {

        String otpStoreOtp = otpStore.get(request.getEmail());

        if (otpStoreOtp == null) {
            return "Please request a new one.";
        }

        if (!request.getOtp().equals(otpStoreOtp)) {
            return "Invalid OTP";
        }


        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        user.setVarified(true);

        return "User registered successfully" ;

    }

    public Map<String, String> login(LoginDto request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        return Map.of("token", jwt);
    }

    public void sendOtpForLogin(String email){

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email not found")));

        String otp = String.valueOf(new Random().nextInt(999999));
        otpStore.put(email, otp); // Store with expiry logic ideally

        EmailRequestDto emailRequestDto1 = new EmailRequestDto();
        emailRequestDto1.setTo(email);
        emailRequestDto1.setBody(otp);
        emailRequestDto1.setSubject("Otp For Verification");

        rabbitTemplate.convertAndSend("job.otp.exchange", "job.otp.notification", emailRequestDto1);

    }

    public void sendOtpForRegister(String email){

        String otp = String.valueOf(new Random().nextInt(999999));
        otpStore.put(email, otp); // Store with expiry logic ideally

        EmailRequestDto emailRequestDto1 = new EmailRequestDto();
        emailRequestDto1.setTo(email);
        emailRequestDto1.setBody(otp);
        emailRequestDto1.setSubject("Otp For Verification");

        rabbitTemplate.convertAndSend("job.otp.exchange", "job.otp.notification", emailRequestDto1);

    }

    public String verify(VerifyDto dto) {

        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        String otpStoreOtp = otpStore.get(dto.getEmail());
        if (otpStoreOtp == null) {
            return "OTP expired or not found. Please request a new one.";
        }

        if (!dto.getOtp().equals(otpStoreOtp)) {
            return "Invalid OTP";
        }

        User user = userOpt.get();
        user.setVarified(true);

        // Encrypt and set new password (if password reset is part of OTP verification)
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedPassword);

        userRepository.save(user);
        otpStore.remove(dto.getEmail());

        return "OTP verified successfully. User is now verified.";
    }



}