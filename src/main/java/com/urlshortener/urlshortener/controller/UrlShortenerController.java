package com.urlshortener.urlshortener.controller;

import com.urlshortener.urlshortener.dto.ShortenRequest;
import com.urlshortener.urlshortener.dto.ShortenResponse;
import com.urlshortener.urlshortener.model.ShortenedUrl;
import com.urlshortener.urlshortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "URL Shortener", description = "API for shortening URLs and resolving short codes")
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/api/shorten")
    @Operation(summary = "Shorten a URL", description = "Accepts a long URL and returns a short code")
    public ResponseEntity<ShortenResponse> shortenUrl(
            @Valid @RequestBody ShortenRequest request,
            HttpServletRequest httpRequest) {
        ShortenedUrl shortened = service.shortenUrl(request.getUrl());
        String baseUrl = httpRequest.getRequestURL().toString().replace("/api/shorten", "");
        ShortenResponse response = new ShortenResponse(
                shortened.getShortCode(),
                baseUrl + "/" + shortened.getShortCode(),
                shortened.getOriginalUrl(),
                shortened.getCreatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Updated: now accepts HttpServletRequest and records analytics before redirecting
    @GetMapping("/{shortCode}")
    @Operation(summary = "Redirect to original URL", description = "Resolves a short code, records analytics, and redirects")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request) {
        return service.resolveUrl(shortCode)
                .map(url -> {
                    service.recordClick(url, request.getHeader("Referer"), request.getHeader("User-Agent"));
                    return ResponseEntity.status(HttpStatus.FOUND)
                            .header(HttpHeaders.LOCATION, url.getOriginalUrl())
                            .<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/urls")
    @Operation(summary = "List all shortened URLs", description = "Returns a paginated list of all shortened URLs")
    public ResponseEntity<Page<ShortenResponse>> listUrls(Pageable pageable, HttpServletRequest httpRequest) {
        String baseUrl = httpRequest.getRequestURL().toString().replace("/api/urls", "");
        Page<ShortenResponse> responses = service.listAll(pageable).map(url ->
                new ShortenResponse(url.getShortCode(), baseUrl + "/" + url.getShortCode(),
                        url.getOriginalUrl(), url.getCreatedAt()));
        return ResponseEntity.ok(responses);
    }

    // New endpoint: returns click count and recent click events for a short code
    @GetMapping("/api/urls/{shortCode}/stats")
    @Operation(summary = "Get URL analytics", description = "Returns click count and recent click events")
    public ResponseEntity<?> getStats(@PathVariable String shortCode) {
        System.out.println("Received request for stats of short code: " + shortCode);
        return service.resolveUrl(shortCode)
                .map(url -> ResponseEntity.ok(service.getStats(url)))
                .orElse(ResponseEntity.notFound().build());
    }
}