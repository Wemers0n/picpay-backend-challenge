package com.example.picpay_backend_challenge.dtos.request;

import java.math.BigDecimal;

public record TransactionRequestDTO(BigDecimal value, Long payer, Long payee) {
}
