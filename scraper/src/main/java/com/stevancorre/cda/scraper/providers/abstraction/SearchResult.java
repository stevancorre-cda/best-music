package com.stevancorre.cda.scraper.providers.abstraction;

/**
 * Represents a search result
 *
 * @param url         The article's url
 * @param provider    The provider used
 * @param imageUrl    The thumbnail url
 * @param title       The article's title
 * @param description The article's description
 * @param year        The article's year
 * @param genre       The article's genre
 * @param price       The article's price
 */
public record SearchResult(String url,
                           Provider provider,
                           String imageUrl,
                           String title,
                           String description,
                           Integer year,
                           Genre genre,
                           double price) {
    @Override
    public String toString() {
        return String.format("""
                        Url: %s
                        Title: %s
                        Description: %s
                        Year: %d
                        Price: %s €""",
                url, title, description, year, price);
    }
}
