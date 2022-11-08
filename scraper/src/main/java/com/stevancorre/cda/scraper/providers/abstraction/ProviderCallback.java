package com.stevancorre.cda.scraper.providers.abstraction;

public interface ProviderCallback {
    void onDone(final SearchResult[] results);

    void onError(final Exception exception);

    void onNext(final float percentage);
}
