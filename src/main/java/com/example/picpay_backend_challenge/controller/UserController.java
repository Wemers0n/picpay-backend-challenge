package com.example.picpay_backend_challenge.controller;

import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.dtos.request.UserRequestDTO;
import com.example.picpay_backend_challenge.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = this.service.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO dto){
        User createdUser = this.service.createUser(dto);
        return new ResponseEntity(createdUser, HttpStatus.CREATED);
    }
}
