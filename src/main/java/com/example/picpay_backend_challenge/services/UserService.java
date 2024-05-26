package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.dtos.request.UserRequestDTO;
import com.example.picpay_backend_challenge.exception.user.UserNotFoundException;
import com.example.picpay_backend_challenge.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long id){
        return this.userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public User createUser(UserRequestDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
