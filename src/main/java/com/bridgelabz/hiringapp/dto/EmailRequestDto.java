package com.bridgelabz.hiringapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

    private String to;
    private String subject;
    private String body;
    private Long notificationId;

}
