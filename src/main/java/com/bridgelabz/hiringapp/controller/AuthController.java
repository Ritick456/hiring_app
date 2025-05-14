package com.bridgelabz.hiringapp.controller;


import com.bridgelabz.hiringapp.dto.*;
import com.bridgelabz.hiringapp.service.AuthService;

import com.bridgelabz.hiringapp.utils.ResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/send-otp")
    public String register(HttpServletRequest request, @RequestBody EmailDto emailDto) {

        authService.sendOtpForRegister(emailDto.getEmail());

//        String msg = authService.register(requestdto);
//        return ResponseBuilder.success(null,msg,request.getRequestURI());
        return "Mail Sent Successfully";
    }

    @PostMapping("/register/verify-registered")
    public ResponseEntity<ApiSuccessResponseDto> registeredVerify(HttpServletRequest request, @RequestBody RegisterDto requestdto) {

        String msg = authService.register(requestdto);

        return ResponseBuilder.success(null,msg,request.getRequestURI());

    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponseDto> login(HttpServletRequest request,@RequestBody LoginDto requestdto) {

        Map<String, String > data =authService.login(requestdto);

        return ResponseBuilder.success(data,"Token generated", request.getRequestURI());
    }


    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody EmailDto request){

        authService.sendOtpForLogin(request.getEmail());

        return "Mail Sent Successfully";
    }

    @PostMapping("/reset-password")
    public String verifyOtp(@RequestBody VerifyDto verifyDto){

        return authService.verify(verifyDto);

    }
}