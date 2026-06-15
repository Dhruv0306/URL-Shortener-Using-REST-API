package com.urlshortener.urlshortener.dto;

import java.time.LocalDateTime;

public record ShortenResponse(String shortCode, String shortUrl, String originalUrl, LocalDateTime createdAt) {
}