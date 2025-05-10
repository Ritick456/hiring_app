package com.bridgelabz.hiringapp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiSuccessResponseDto {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private Object data;

}
