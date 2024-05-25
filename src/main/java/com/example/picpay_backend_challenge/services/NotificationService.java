package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.dtos.NotificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;

    public void sendNotification(User user, String message){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
        System.out.println("notification sent to the user");
    }
}
