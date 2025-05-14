package com.bridgelabz.hiringapp.dto;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;


@Data
public class EmailDto {

    @Email
    private String email;

}