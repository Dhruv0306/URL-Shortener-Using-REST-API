package com.urlshortener.urlshortener.service;

import com.urlshortener.urlshortener.dto.ClickEventDTO;
import com.urlshortener.urlshortener.model.ClickEvent;
import com.urlshortener.urlshortener.model.ShortenedUrl;
import com.urlshortener.urlshortener.repository.ClickEventRepository;
import com.urlshortener.urlshortener.repository.ShortenedUrlRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UrlShortenerService {

    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();
    private final ShortenedUrlRepository repository;
    private final ClickEventRepository clickEventRepository;

    public UrlShortenerService(ShortenedUrlRepository repository, ClickEventRepository clickEventRepository) {
        this.repository = repository;
        this.clickEventRepository = clickEventRepository;
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

    // Records a click event with referrer and user-agent metadata
    public void recordClick(ShortenedUrl shortenedUrl, String referrer, String userAgent) {
        ClickEvent event = new ClickEvent();
        event.setShortenedUrl(shortenedUrl);
        event.setReferrer(referrer);
        event.setUserAgent(userAgent);
        clickEventRepository.save(event);
    }

    // Returns total click count and the 10 most recent click events
    public Map<String, Object> getStats(ShortenedUrl shortenedUrl) {
        long count = clickEventRepository.countByShortenedUrl(shortenedUrl);
        List<ClickEventDTO> recent = clickEventRepository.findTop10ByShortenedUrlOrderByClickedAtDesc(shortenedUrl).stream().map(ClickEventDTO::new).toList();
        return Map.of("totalClicks", count, "recentClicks", recent);
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