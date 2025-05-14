package com.bridgelabz.hiringapp.dto;

import lombok.Data;

@Data
public class VerifyDto {

    private String email;
    private String otp;
    private String password;
}
