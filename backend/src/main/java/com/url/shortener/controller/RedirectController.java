package com.url.shortener.controller;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.service.RateLimiterService;
import com.url.shortener.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@AllArgsConstructor
public class RedirectController {

    private UrlMappingService urlMappingService;
    private RateLimiterService rateLimiterService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl, HttpServletRequest request) {
        // Get client IP address
        String clientIp = request.getRemoteAddr();

        // Check rate limit
        if (!rateLimiterService.allowRequest(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        UrlMapping urlMapping = urlMappingService.getOriginalUrl(shortUrl);
        if (urlMapping != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", urlMapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(httpHeaders).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}