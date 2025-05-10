package com.bridgelabz.hiringapp.utils;

import com.bridgelabz.hiringapp.dto.ApiErrorResponseDto;
import com.bridgelabz.hiringapp.dto.ApiSuccessResponseDto;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseBuilder {

    public static ResponseEntity<ApiSuccessResponseDto> success(Object data, String path, String message) {
        ApiSuccessResponseDto res = ApiSuccessResponseDto.builder()
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();

        return ResponseEntity.ok(res);
    }
}
