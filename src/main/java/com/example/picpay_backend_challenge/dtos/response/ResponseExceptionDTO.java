package com.example.picpay_backend_challenge.dtos.response;

import org.springframework.http.HttpStatus;

public record ResponseExceptionDTO(String message, Integer statusCode) {
}
