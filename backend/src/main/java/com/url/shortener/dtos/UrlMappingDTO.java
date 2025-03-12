package com.url.shortener.dtos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for URL mapping information
 * Used to transfer URL data between backend and frontend
 */
@Data
public class UrlMappingDTO {
    // Unique identifier for the URL mapping
    private Long id;
    
    // Original URL that was shortened
    private String originalUrl;
    
    // Generated short URL identifier
    private String shortUrl;
    
    // Number of times this URL has been accessed
    private int clickCount;
    
    // When this URL mapping was created
    private LocalDateTime createdDate;
    
    // Username of the URL owner
    private String username;
}
