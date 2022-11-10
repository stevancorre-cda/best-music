package com.stevancorre.cda.scraper.providers.abstraction;

/**
 * Represents a query
 *
 * @param query    The query string
 * @param genre    The genre
 * @param minPrice The minimum price
 * @param maxPrice The maximum price
 * @param year     The year
 */
public record SearchQuery(String query,
                          Genre genre,
                          Double minPrice,
                          Double maxPrice,
                          Integer year) {
    /**
     * Check if whether the price range is set
     *
     * @return True if it was set. Otherwise false
     */
    public boolean hasPrice() {
        return minPrice != null && maxPrice != null;
    }

    /**
     * Check if whether the year is set
     *
     * @return True if it was set. Otherwise false
     */
    public boolean hasYear() {
        return year() != null;
    }

    /**
     * Check if whether the genre is set
     *
     * @return True if it was set. Otherwise false
     */
    public boolean hasGenre() {
        return genre() != null;
    }
}
