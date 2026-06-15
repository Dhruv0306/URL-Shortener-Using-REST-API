package com.urlshortener.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "click_events")
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links this click event to the shortened URL it belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shortened_url_id", nullable = false)
    private ShortenedUrl shortenedUrl;

    @Column(nullable = false)
    private LocalDateTime clickedAt;

    // Where the visitor came from (nullable because not all requests include Referer)
    @Column(length = 2048)
    private String referrer;

    // Browser or tool identifier
    @Column(length = 512)
    private String userAgent;

    @PrePersist
    protected void onCreate() {
        this.clickedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ShortenedUrl getShortenedUrl() { return shortenedUrl; }
    public void setShortenedUrl(ShortenedUrl shortenedUrl) { this.shortenedUrl = shortenedUrl; }
    public LocalDateTime getClickedAt() { return clickedAt; }
    public void setClickedAt(LocalDateTime clickedAt) { this.clickedAt = clickedAt; }
    public String getReferrer() { return referrer; }
    public void setReferrer(String referrer) { this.referrer = referrer; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}