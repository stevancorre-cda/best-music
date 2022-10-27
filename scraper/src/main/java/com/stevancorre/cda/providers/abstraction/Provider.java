package com.stevancorre.cda.providers.abstraction;

import com.gargoylesoftware.htmlunit.WebClient;

public abstract class Provider {
    protected final WebClient client;

    protected Provider() {
        this.client = new WebClient() {{
            getOptions().setUseInsecureSSL(false);
            getOptions().setCssEnabled(false);
            getOptions().setJavaScriptEnabled(false);
        }};
    }

    public static Provider[] getProviders() {
        return ProvidersReflection.providers.toArray(new Provider[0]);
    }

    public abstract SearchResult[] query(final String query, final int limit);

    public final SearchResult[] query(final String query) {
        return query(query, Integer.MAX_VALUE);
    }

    public final String getName() {
        return this.getClass().getSimpleName().replace("Provider", "");
    }
}
