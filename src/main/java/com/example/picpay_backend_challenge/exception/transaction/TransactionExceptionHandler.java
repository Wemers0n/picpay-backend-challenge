package com.example.picpay_backend_challenge.exception.transaction;

import com.example.picpay_backend_challenge.dtos.response.ResponseExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<ResponseExceptionDTO> unauthorizedTransactionException(UnauthorizedTransactionException exception){
        ResponseExceptionDTO responseDTO = new ResponseExceptionDTO(exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity(responseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<ResponseExceptionDTO> InvalidTransaction(InvalidTransactionException exception){
        ResponseExceptionDTO responseDTO = new ResponseExceptionDTO(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(responseDTO);
    }
}

