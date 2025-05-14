package com.bridgelabz.hiringapp.dto;

import com.bridgelabz.hiringapp.entity.Role;
import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private Role role;
}