package ru.mozevil.test.mitra.Client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.mozevil.test.mitra.Client.pojo.LoginRequest;
import ru.mozevil.test.mitra.Client.pojo.TokenResponse;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final RestTemplate restTemplate;

    @Value("${app.mainAppUrl}")
    private String mainAppUrl;

    @GetMapping("/list")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        HttpEntity<String> entity = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            entity = new HttpEntity<>(headers);
        }
        return restTemplate.exchange(mainAppUrl + "/user/dto", GET, entity, List.class);
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> auth(@RequestBody LoginRequest loginRequest) {
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest);
        return restTemplate.exchange(mainAppUrl + "/auth", POST, entity, TokenResponse.class);
    }
}
