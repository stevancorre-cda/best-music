package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.util.List;

public class VinylcornerProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return null;
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return null;
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement source) {
        return null;
    }
}