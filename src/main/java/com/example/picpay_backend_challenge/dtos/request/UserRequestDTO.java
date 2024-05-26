package com.example.picpay_backend_challenge.dtos.request;

import com.example.picpay_backend_challenge.domain.user.UserType;

import java.math.BigDecimal;

public record UserRequestDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}