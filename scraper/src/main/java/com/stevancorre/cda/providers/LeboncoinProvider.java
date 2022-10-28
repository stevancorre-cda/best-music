package com.stevancorre.cda.providers;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.stevancorre.cda.providers.abstraction.Provider;
import com.stevancorre.cda.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

public final class LeboncoinProvider extends Provider<HtmlAnchor> {
    @Override
    protected String getQueryUrl(String query) {
        return String.format("https://www.leboncoin.fr/recherche?text=%s", query.replace(" ", "%20"));
    }

    @Override
    protected List<HtmlAnchor> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//a[@data-qa-id='aditem_container']");
    }

    @Override
    protected SearchResult scrapNext(final HtmlAnchor anchor) throws IOException {
        final String url = String.format("https://www.leboncoin.fr%s", anchor.getHrefAttribute());
        final HtmlPage page = client.getPage(url);

        final HtmlHeading1 title = page.getFirstByXPath("//h1[@data-qa-id='adview_title']");
        final HtmlDivision price = page.getFirstByXPath("//div[@data-qa-id='adview_price']");
        final HtmlDivision description = page.getFirstByXPath("//div[@data-qa-id='adview_description_container']");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                title.getTextContent(),
                description == null ? "No description" : description.getTextContent(),
                scrapPrice(price));
    }

    private static Double scrapPrice(final HtmlDivision price) {
        if (price == null) return 0.0;

        final String priceText = price
                .getTextContent()
                .replaceAll("(\\d+).*€.*", "$1");
        return Double.parseDouble(priceText);
    }
}
