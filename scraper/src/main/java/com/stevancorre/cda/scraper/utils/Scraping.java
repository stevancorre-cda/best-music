package com.stevancorre.cda.scraper.utils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.Calendar;
import java.util.Date;

import static com.stevancorre.cda.scraper.utils.Parsing.parsePrice;
import static com.stevancorre.cda.scraper.utils.Parsing.parseYear;

public final class Scraping {
    private Scraping() {
    }

    public static double scrapPrice(final HtmlElement element) {
        if (element == null) return 0;

        return parsePrice(element.getTextContent());
    }

    public static String scrapDescription(final HtmlElement element) {
        if (element == null) return "No description";

        return element.getTextContent();
    }

    public static Integer scrapYear(final HtmlElement element) {
        if (element == null) return null;

        return parseYear(element.getTextContent());
    }
}
