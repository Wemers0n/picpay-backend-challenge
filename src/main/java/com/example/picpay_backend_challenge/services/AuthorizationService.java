package com.example.picpay_backend_challenge.services;

import com.example.picpay_backend_challenge.domain.user.User;
import com.example.picpay_backend_challenge.exception.transaction.InvalidTransactionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final RestTemplate restTemplate;

    @Value("${app.authorizationApi}")
    private String authApiUrl;

    public boolean authorizeTransaction(){
        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(this.authApiUrl, Map.class);

            if(authorizationResponse.getStatusCode() == HttpStatus.OK){
                Map<String, Object> message = (Map<String, Object>) authorizationResponse.getBody().get("data");
                boolean authResult = (boolean) message.get("authorization");
                return authResult;
//                return (boolean) message.get("authorization");
            }
            return false;
        } catch (HttpClientErrorException e){
            throw new InvalidTransactionException("Invalid transaction");
        }
    }
}
