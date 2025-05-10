package com.bridgelabz.hiringapp.exception;


import com.bridgelabz.hiringapp.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(CandidateNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> candidateNotFoundException(CandidateNotFoundException ex , WebRequest request){

        ApiErrorResponseDto responseDto = ApiErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Candidate Not Found")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);


    }
}
