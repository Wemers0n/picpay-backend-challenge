package com.example.picpay_backend_challenge.exception.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
