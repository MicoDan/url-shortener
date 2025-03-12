package com.url.shortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.url.shortener.security.jwt.JwtUtils;
import com.url.shortener.service.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for UrlMappingController
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UrlMappingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    private String authToken;

    @BeforeEach
    void setUp() {
        UserDetailsImpl userDetails = new UserDetailsImpl(
            1L, 
            "testuser", 
            "test@example.com", 
            "password123",
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        authToken = jwtUtils.generateToken(userDetails);
    }

    @Test
    void createShortUrl_Success() throws Exception {
        Map<String, String> request = new HashMap<>();
        request.put("originalUrl", "https://example.com");

        mockMvc.perform(post("/urls/shorten")
                .header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getUserUrls_Success() throws Exception {
        mockMvc.perform(get("/urls/")
                .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());
    }

    @Test
    void getUrlAnalytics_Success() throws Exception {
        mockMvc.perform(get("/urls/analytics/abc123")
                .header("Authorization", "Bearer " + authToken)
                .param("startDate", "2024-01-01T00:00:00")
                .param("endDate", "2024-12-31T23:59:59"))
                .andExpect(status().isOk());
    }
} 