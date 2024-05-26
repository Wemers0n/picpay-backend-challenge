package com.example.picpay_backend_challenge.controller;

import com.example.picpay_backend_challenge.domain.transaction.Transaction;
import com.example.picpay_backend_challenge.dtos.request.TransactionRequestDTO;
import com.example.picpay_backend_challenge.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequestDTO dto){
        var createdTransaction = this.service.createTransaction(dto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}
