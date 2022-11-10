package com.stevancorre.cda.scraper.providers;

import com.gargoylesoftware.htmlunit.html.*;
import com.stevancorre.cda.scraper.providers.abstraction.Provider;
import com.stevancorre.cda.scraper.providers.abstraction.SearchQuery;
import com.stevancorre.cda.scraper.providers.abstraction.SearchResult;

import java.io.IOException;
import java.util.List;

import static com.stevancorre.cda.scraper.utils.Formatting.formatQuery;
import static com.stevancorre.cda.scraper.utils.Scraping.*;

public final class FnacProvider extends Provider {
    @Override
    protected String getQueryUrl(final SearchQuery query) {
        return String.format(
                "https://www.fnac.com/SearchResult/ResultList.aspx?SCat=Musique%%211&Search=%s",
                formatQuery(query.query(), "+"));
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
        final HtmlParagraph year = page.getFirstByXPath("/html/body/div[2]/div/div[1]/div[2]/div[2]/div[2]/div[2]/dl[2]/dd/p");

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
