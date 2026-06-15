package com.urlshortener.urlshortener.dto;

import com.urlshortener.urlshortener.model.ClickEvent;

import java.time.LocalDateTime;

public class ClickEventDTO {
    private LocalDateTime clickedAt;
    private String referrer;
    private String userAgent;

    public ClickEventDTO(ClickEvent event) {
        this.clickedAt = event.getClickedAt();
        this.referrer = event.getReferrer();
        this.userAgent = event.getUserAgent();
    }

    public LocalDateTime getClickedAt() {
        return clickedAt;
    }

    public void setClickedAt(LocalDateTime clickedAt) {
        this.clickedAt = clickedAt;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}