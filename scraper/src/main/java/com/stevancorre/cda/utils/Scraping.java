package com.stevancorre.cda.utils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import static com.stevancorre.cda.utils.Parsing.parsePrice;

public final class Scraping {
    private Scraping() {
    }

    public static double scrapPrice(final HtmlElement element) {
        if (element == null) return 0;

        return parsePrice(element.getTextContent());
    }
}
