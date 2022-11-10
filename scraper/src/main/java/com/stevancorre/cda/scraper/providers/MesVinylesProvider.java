package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchQuery;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Formatting.formatQuery;
import static com.stevancorre.cda.scraper.utils.Scraping.*;

public final class MesVinylesProvider extends Provider {
    @Override
    protected String getQueryUrl(final SearchQuery query) {
        return String.format(
                "https://mesvinyles.fr/en/search?controller=search&s=%s",
                formatQuery(query.query(), "+"));
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
        final HtmlParagraph year = page.getFirstByXPath("/html/body/main/section/div/div/div/section/div[1]/div[2]/div[2]/div[1]/p[1]");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                title.getTextContent(),
                scrapDescription(description),
                scrapYear(year),
                null,
                scrapPrice(price));
    }
}