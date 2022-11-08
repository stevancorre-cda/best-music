package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Scraping.scrapDescription;
import static com.stevancorre.cda.scraper.utils.Scraping.scrapPrice;

public final class LeboncoinProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return String.format("https://www.leboncoin.fr/recherche?text=%s", query.replace(" ", "%20"));
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//a[@data-qa-id='aditem_container']");
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement anchor) throws IOException {
        final String url = String.format("https://www.leboncoin.fr%s", ((HtmlAnchor) anchor).getHrefAttribute());
        final HtmlPage page = client.getPage(url);

        final HtmlHeading1 title = page.getFirstByXPath("//h1[@data-qa-id='adview_title']");
        final HtmlDivision price = page.getFirstByXPath("//div[@data-qa-id='adview_price']");
        final HtmlDivision description = page.getFirstByXPath("//div[@data-qa-id='adview_description_container']");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                title.getTextContent(),
                scrapDescription(description),
                scrapPrice(price));
    }
}
