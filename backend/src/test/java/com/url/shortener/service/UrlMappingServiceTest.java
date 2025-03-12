package com.url.shortener.service;

import com.url.shortener.dtos.UrlMappingDTO;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.UrlMappingRepository;
import com.url.shortener.repository.ClickEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UrlMappingService
 */
@ExtendWith(MockitoExtension.class)
public class UrlMappingServiceTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private ClickEventRepository clickEventRepository;

    @InjectMocks
    private UrlMappingService urlMappingService;

    private User testUser;
    private UrlMapping testUrlMapping;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        testUrlMapping = new UrlMapping();
        testUrlMapping.setId(1L);
        testUrlMapping.setOriginalUrl("https://example.com");
        testUrlMapping.setShortUrl("abc123");
        testUrlMapping.setUser(testUser);
    }

    @Test
    void createShortUrl_Success() {
        when(urlMappingRepository.save(any())).thenReturn(testUrlMapping);

        UrlMappingDTO result = urlMappingService.createShortUrl("https://example.com", testUser);

        assertNotNull(result);
        assertEquals(testUrlMapping.getOriginalUrl(), result.getOriginalUrl());
        verify(urlMappingRepository).save(any());
    }

    @Test
    void getUrlsByUser_Success() {
        when(urlMappingRepository.findByUser(testUser))
            .thenReturn(Arrays.asList(testUrlMapping));

        List<UrlMappingDTO> results = urlMappingService.getUrlsByUser(testUser);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(testUrlMapping.getOriginalUrl(), results.get(0).getOriginalUrl());
    }

    @Test
    void getOriginalUrl_Success() {
        when(urlMappingRepository.findByShortUrl("abc123")).thenReturn(testUrlMapping);
        when(urlMappingRepository.save(any())).thenReturn(testUrlMapping);

        UrlMapping result = urlMappingService.getOriginalUrl("abc123");

        assertNotNull(result);
        assertEquals(testUrlMapping.getOriginalUrl(), result.getOriginalUrl());
        verify(clickEventRepository).save(any());
    }
} 