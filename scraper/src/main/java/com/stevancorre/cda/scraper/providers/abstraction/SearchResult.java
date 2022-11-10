package com.stevancorre.cda.scraper.providers.abstraction;

public record SearchResult(String url,
                           Provider provider,
                           String imageUrl,
                           String title,
                           String description,
                           Integer year,
                           Genre genre,
                           double price) {
}
