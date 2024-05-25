package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.domain.user.UserType;
import com.example.picpay_backend_challenge.exception.BussinessException;
import com.example.picpay_backend_challenge.exception.UserNotFoundException;
import com.example.picpay_backend_challenge.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) {
        if(sender.getUserType() == UserType.MERCHANT){
            throw new BussinessException("Merchant type user is not authorized to carry out transactions");
        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new BussinessException("User does not have enough balance to carry out the transaction");
        }
    }

    public User findUserById(Long id){
        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }
}
