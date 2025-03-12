package com.url.shortener.repository;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Repository tests for UrlMapping
 */
@DataJpaTest
public class UrlMappingRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Test
    void findByShortUrl_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        entityManager.persist(user);

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl("https://example.com");
        urlMapping.setShortUrl("abc123");
        urlMapping.setUser(user);
        entityManager.persist(urlMapping);
        entityManager.flush();

        UrlMapping found = urlMappingRepository.findByShortUrl("abc123");
        assertNotNull(found);
        assertEquals("abc123", found.getShortUrl());
    }

    @Test
    void findByUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        entityManager.persist(user);

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl("https://example.com");
        urlMapping.setShortUrl("abc123");
        urlMapping.setUser(user);
        entityManager.persist(urlMapping);
        entityManager.flush();

        List<UrlMapping> found = urlMappingRepository.findByUser(user);
        assertFalse(found.isEmpty());
        assertEquals(1, found.size());
        assertEquals("abc123", found.get(0).getShortUrl());
    }
} 