package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.utils.Scraping.scrapPrice;

public final class CulturefactoryProvider extends Provider<HtmlArticle> {
    @Override
    protected String getQueryUrl(final String query) {
        return String.format("https://culturefactory.fr/recherche?controller=search&s=%s", query.replace(" ", "+"));
    }

    @Override
    protected List<HtmlArticle> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//article[@class='js-product-miniature item_in']");
    }

    @Override
    protected SearchResult scrapNext(final HtmlArticle source) throws IOException {
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
                description == null ? "No description" : description.getTextContent(),
                scrapPrice(price));
    }
}
