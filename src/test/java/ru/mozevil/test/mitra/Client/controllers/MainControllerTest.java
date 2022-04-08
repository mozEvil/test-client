package ru.mozevil.test.mitra.Client.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import ru.mozevil.test.mitra.Client.pojo.LoginRequest;
import ru.mozevil.test.mitra.Client.pojo.TokenResponse;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static ru.mozevil.test.mitra.Client.configurations.AppConfig.remoteApiUrl;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock private RestTemplate restTemplate;
    private MainController underTest;

    @BeforeEach
    void setUp() {
        underTest = new MainController(restTemplate);
    }

    @Test
    void getUsers_wHeader() {
        String authHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImV4cCI6MTY0OTQyMjU2NX0.15ZppkAtZE8Zc9BlVBOcaxWfBo0ozR4F2p34FHhlZwM";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //when
        underTest.getUsers(authHeader);
        //then
        verify(restTemplate).exchange(remoteApiUrl + "/user/dto", GET, entity, List.class);
    }

    @Test
    void getUsers_woHeader() {
        String authHeader = null;
        HttpEntity<String> entity = null;
        //when
        underTest.getUsers(authHeader);
        //then
        verify(restTemplate).exchange(remoteApiUrl + "/user/dto", GET, entity, List.class);
    }

    @Test
    void auth() {
        LoginRequest loginRequest = new LoginRequest("user","pass");
        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest);
        //when
        underTest.auth(loginRequest);
        //then
        verify(restTemplate).exchange(remoteApiUrl + "/auth", POST, entity, TokenResponse.class);

    }
}