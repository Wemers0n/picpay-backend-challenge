package com.example.picpay_backend_challenge.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long payer, Long payee) {
}
