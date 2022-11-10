package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchQuery;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Formatting.formatQuery;
import static com.stevancorre.cda.scraper.utils.Formatting.removeExtraSpaces;
import static com.stevancorre.cda.scraper.utils.Scraping.*;

/**
 * Provider for discogs.com
 */
public final class DiscogsProvider extends Provider {
    @Override
    protected String getQueryUrl(final SearchQuery query) {
        // initialize query params
        final String genreParam = query.hasGenre() ?
                String.format("&genre=%s", query.genre()) : "";
        final String priceParam = query.hasPrice() ?
                String.format("&price1=%f&price2=%f", query.minPrice(), query.maxPrice()) : "";
        final String yearParam = query.hasYear() ?
                String.format("&year=%d", query.year()) : "";

        return String.format(
                "https://www.discogs.com/fr/sell/list?format=Vinyl&currency=EUR&q=%s%s%s%s",
                formatQuery(query.query(), "+"),
                genreParam,
                priceParam,
                yearParam);
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
        final HtmlDivision year = page.getFirstByXPath("/html/body/div[1]/div[4]/div[1]/div/div[1]/div/div[1]/div[9]");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                removeExtraSpaces(title.getTextContent()),
                scrapDescription(null),
                scrapYear(year),
                null,
                scrapPrice(price));
    }
}