package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.util.List;

import static com.stevancorre.cda.utils.Formatting.formatQuery;

public final class DiscogsProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return null;
    }

    @Override
    protected List<HtmlElement> scrapEntries(HtmlPage page) {
        return null;
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement source) {
        return null;
    }
}