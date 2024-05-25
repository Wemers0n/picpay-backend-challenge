package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.transaction.Transaction;
import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.dtos.TransactionDTO;
import com.example.picpay_backend_challenge.exception.UnauthorizedTransactionException;
import com.example.picpay_backend_challenge.respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;


    public Transaction createTransaction(TransactionDTO transactionDTO){
        User sender = this.userService.findUserById(transactionDTO.payer());
        User receiver = this.userService.findUserById(transactionDTO.payee());

        userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transactionDTO.value());
        if(isAuthorized){
            throw new UnauthorizedTransactionException("Unauthorized Transaction");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transaction completed successfully");
        this.notificationService.sendNotification(receiver, "Transaction received successfully");

        return newTransaction;
    }
}

