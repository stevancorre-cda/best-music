package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.utils.Formatting.formatQuery;
import static com.stevancorre.cda.utils.Formatting.removeExtraSpaces;
import static com.stevancorre.cda.utils.Scraping.scrapDescription;
import static com.stevancorre.cda.utils.Scraping.scrapPrice;

public final class DiscogsProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return String.format(
                "https://www.discogs.com/fr/sell/list?format=Vinyl&currency=EUR&q=%s",
                formatQuery(query, "+"));
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//tr[@class='shortcut_navigable ']");
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement source) throws IOException {
        final HtmlAnchor anchor = source.getFirstByXPath(".//a");
        final String url = String.format("https://www.discogs.com%s", anchor.getHrefAttribute());
        final HtmlPage page = client.getPage(url);

        final HtmlHeading1 title = page.getFirstByXPath("//h1[@id='profile_title']");
        final HtmlSpan price = page.getFirstByXPath("//span[@class='price']");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                removeExtraSpaces(title.getTextContent()),
                scrapDescription(null),
                scrapPrice(price));
    }
}