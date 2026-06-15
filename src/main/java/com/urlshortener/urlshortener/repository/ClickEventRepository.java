package com.urlshortener.urlshortener.repository;

import com.urlshortener.urlshortener.model.ClickEvent;
import com.urlshortener.urlshortener.model.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    // Count total clicks for a given shortened URL
    long countByShortenedUrl(ShortenedUrl shortenedUrl);

    // Get the 10 most recent click events for a given shortened URL
    List<ClickEvent> findTop10ByShortenedUrlOrderByClickedAtDesc(ShortenedUrl shortenedUrl);
}