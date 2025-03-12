package com.url.shortener.controller;

import com.url.shortener.dtos.ClickEventDTO;
import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.User;
import com.url.shortener.service.RateLimiterService;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/urls")
@AllArgsConstructor
public class UrlMappingController {
    private UrlMappingService urlMappingService;
    private UserService userService;
    private RateLimiterService rateLimiterService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> request,
                                                        Principal principal) {
        // Get authenticated user's username
        String username = principal.getName();

        // Check rate limit
        if (!rateLimiterService.allowRequest(username)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(username);
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDTO);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        // Get authenticated user's username
        String username = principal.getName();

        // Check rate limit
        if (!rateLimiterService.allowRequest(username)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        User user = userService.findByUsername(username);
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate,
                                                               Principal principal) {
        // Get authenticated user's username
        String username = principal.getName();

        // Check rate limit
        if (!rateLimiterService.allowRequest(username)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate) {
        // Get authenticated user's username
        String username = principal.getName();

        // Check rate limit
        if (!rateLimiterService.allowRequest(username)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user = userService.findByUsername(username);
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }
}