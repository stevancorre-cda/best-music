package com.stevancorre.cda.scraper.providers.abstraction;

public interface ProviderCallback {
    void onNext(final float percentage);

    void onDone(final SearchResult[] results);

    void onError(final Exception exception);
}
