package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.util.List;

public class DiscogsProvider extends Provider {
    @Override
    protected String getQueryUrl(String query) {
        return null;
    }

    @Override
    protected List scrapEntries(HtmlPage page) {
        return null;
    }

    @Override
    protected SearchResult scrapNext(Object source) {
        return null;
    }
}