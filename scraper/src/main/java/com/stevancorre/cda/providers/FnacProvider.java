package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.util.List;

public final class FnacProvider extends Provider {
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
