package com.bridgelabz.hiringapp.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;


@Data
@Entity
public class ForgotPasswordRequest {

    @Id
    private Long id;
    @Email
    private String email;
}