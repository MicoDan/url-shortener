package com.url.shortener.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    // Store buckets for each user (or IP)
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    // The rate limit: 60 requests per minute per user
    private final Bandwidth limit = Bandwidth.classic(60, Refill.intervally(60, Duration.ofMinutes(1)));

    public boolean allowRequest(String key) {
        // Get or create a bucket for the user
        Bucket bucket = buckets.computeIfAbsent(key, k -> Bucket4j.builder().addLimit(limit).build());

        // Try to consume a token from the bucket
        return bucket.tryConsume(1);
    }
}