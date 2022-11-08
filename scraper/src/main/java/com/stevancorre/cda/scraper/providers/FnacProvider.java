package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Scraping.scrapDescription;
import static com.stevancorre.cda.scraper.utils.Scraping.scrapPrice;

public final class FnacProvider extends Provider {
    @Override
    protected String getQueryUrl(final String query) {
        return String.format(
                "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=Musique%%211&Search=%s",
                query.replace(" ", "+"));
    }

    @Override
    protected List<HtmlElement> scrapEntries(final HtmlPage page) {
        return page.getByXPath("//div[@class='clearfix Article-item js-Search-hashLinkId']");
    }

    @Override
    protected SearchResult scrapNext(final HtmlElement source) throws IOException {
        final HtmlAnchor anchor = source.getFirstByXPath(".//a");
        final String url = anchor.getHrefAttribute();
        final HtmlPage page = client.getPage(url);

        final HtmlHeading1 title = page.getFirstByXPath("//h1[@class='f-productHeader-Title']");
        final HtmlSpan price = page.getFirstByXPath("//span[@class='f-faPriceBox__price userPrice js-ProductBuy-standardCheckable checked']");
        final HtmlDivision description = page.getFirstByXPath("//div[@class='f-productDesc__raw']");

        return new SearchResult(
                url,
                this,
                "https://via.placeholder.com/500",
                title.getTextContent(),
                scrapDescription(description),
                scrapPrice(price));
    }
}
