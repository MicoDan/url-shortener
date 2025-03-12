package com.url.shortener.repository;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for URL mapping operations
 * Handles database interactions for shortened URLs
 */
@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    // Find URL mapping by short URL identifier
    UrlMapping findByShortUrl(String shortUrl);
    
    // Find all URL mappings for a specific user
    List<UrlMapping> findByUser(User user);
}
