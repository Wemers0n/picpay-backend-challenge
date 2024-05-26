package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.transaction.Transaction;
import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.domain.user.UserType;
import com.example.picpay_backend_challenge.dtos.request.TransactionRequestDTO;
import com.example.picpay_backend_challenge.exception.transaction.InvalidTransactionException;
import com.example.picpay_backend_challenge.exception.transaction.UnauthorizedTransactionException;
import com.example.picpay_backend_challenge.respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;


    public Transaction createTransaction(TransactionRequestDTO transactionRequestDTO){
        User sender = this.userService.findUserById(transactionRequestDTO.payer());
        User receiver = this.userService.findUserById(transactionRequestDTO.payee());

        validateTransaction(sender, receiver, transactionRequestDTO.value());

//        boolean isAuthorized = this.authorizationService.authorizeTransaction(sender, transactionDTO.value());
        if(!authorizationService.authorizeTransaction()){
            throw new UnauthorizedTransactionException("Unauthorized Transaction");
        }

        Transaction newTransaction = saveTransaction(transactionRequestDTO, sender, receiver);
        updateBalances(sender, receiver, transactionRequestDTO.value());

        this.transactionRepository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        notifyUsers(sender, receiver);
        return newTransaction;
    }

    private void validateTransaction(User sender, User receiver, BigDecimal value) {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new InvalidTransactionException("Merchant type user is not authorized to carry out transactions");
        }
        if(sender.getBalance().compareTo(value) < 0){
            throw new InvalidTransactionException("User does not have enough balance to carry out the transaction");
        }
        if(sender.getId().equals(receiver.getId())){
            throw new InvalidTransactionException("user cannot send values to himself");
        }
    }

    private Transaction saveTransaction(TransactionRequestDTO transactionRequestDTO, User sender, User receiver){
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionRequestDTO.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());
        return newTransaction;
    }

    private void updateBalances(User sender, User receiver, BigDecimal value){
        sender.setBalance(sender.getBalance().subtract(value));
        receiver.setBalance(receiver.getBalance().add(value));
    }

    private void notifyUsers(User sender, User receiver){
        this.notificationService.sendNotification(sender, "Transaction completed successfully");
        this.notificationService.sendNotification(receiver, "Transaction received successfully");
    }
}

