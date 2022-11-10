package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchQuery;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Scraping.scrapDescription;
import static com.stevancorre.cda.scraper.utils.Scraping.scrapPrice;

public final class CulturefactoryProvider extends Provider {
    @Override
    protected String getQueryUrl(final SearchQuery query) {
        return String.format("https://culturefactory.fr/recherche?controller=search&s=%s",
                query.query().replace(" ", "+"));
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//article[@class='js-product-miniature item_in']");
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
                null,
                null,
                scrapPrice(price));
    }
}
