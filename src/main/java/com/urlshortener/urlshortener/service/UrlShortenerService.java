package com.urlshortener.urlshortener.service;

import com.urlshortener.urlshortener.model.ShortenedUrl;
import com.urlshortener.urlshortener.repository.ShortenedUrlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();
    private final ShortenedUrlRepository repository;

    public UrlShortenerService(ShortenedUrlRepository repository) {
        this.repository = repository;
    }

    public ShortenedUrl shortenUrl(String originalUrl) {
        String shortCode = generateUniqueCode();
        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortCode(shortCode);
        shortenedUrl.setOriginalUrl(originalUrl);
        return repository.save(shortenedUrl);
    }

    public Optional<ShortenedUrl> resolveUrl(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    public Page<ShortenedUrl> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = generateBase62Code();
        } while (repository.existsByShortCode(code));
        return code;
    }

    private String generateBase62Code() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(BASE62_CHARS.charAt(random.nextInt(BASE62_CHARS.length())));
        }
        return sb.toString();
    }
}