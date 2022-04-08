package ru.mozevil.test.mitra.Client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.mozevil.test.mitra.Client.pojo.TokenResponse;
import ru.mozevil.test.mitra.Client.pojo.LoginRequest;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static ru.mozevil.test.mitra.Client.configurations.AppConfig.remoteApiUrl;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final RestTemplate restTemplate;

    @GetMapping("/list")
    public ResponseEntity<?> getUsers(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        HttpEntity<String> entity = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            entity = new HttpEntity<>(headers);
        }
        return restTemplate.exchange(remoteApiUrl + "/user/dto", GET, entity, List.class);
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenResponse> auth(@RequestBody LoginRequest loginRequest) {
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest);
        return restTemplate.exchange(remoteApiUrl + "/auth", POST, entity, TokenResponse.class);
    }
}
