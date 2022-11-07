package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.utils.Formatting.formatQuery;
import static com.stevancorre.cda.utils.Scraping.scrapDescription;
import static com.stevancorre.cda.utils.Scraping.scrapPrice;

public final class MesVinylesProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return String.format(
                "https://mesvinyles.fr/en/search?controller=search&s=%s",
                formatQuery(query, "+"));
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//li[contains(@class, 'product_item')]");
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement source) throws IOException {
        final HtmlAnchor anchor = source.getFirstByXPath(".//a");
        final String url = anchor.getHrefAttribute();
        final HtmlPage page = client.getPage(url);

        final HtmlHeading1 title = page.getFirstByXPath("//h1[@itemprop='name']");
        final HtmlSpan price = page.getFirstByXPath("//span[@itemprop='price']");
        final HtmlDivision description = page.getFirstByXPath("//div[@itemprop='description']");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                title.getTextContent(),
                scrapDescription(description),
                scrapPrice(price));
    }
}