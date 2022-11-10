package com.stevancorre.cda.scraper.providers.abstraction;

/**
 * Interface for the provider's callbacks
 */
public interface ProviderCallback {
    /**
     * Called when a new entry is scraped
     *
     * @param percentage Progression in this provider
     */
    void onNext(final float percentage);

    /**
     * Called when the scraping is done
     *
     * @param results Array of scraped results
     */
    void onDone(final SearchResult[] results);

    /**
     * Called when an error happens
     *
     * @param exception The exception
     */
    void onError(final Exception exception);
}
