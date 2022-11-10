package com.stevancorre.cda.scraper.providers.abstraction;

public record SearchQuery(String query,
                          Genre genre,
                          Double minPrice,
                          Double maxPrice,
                          Integer year) {
    public boolean hasPrice() {
        return minPrice != null && maxPrice != null;
    }

    public boolean hasYear() {
        return year() != null;
    }

    public boolean hasGenre() {
        return genre() != null;
    }
}
