package com.stevancorre.cda.scraper.utils;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.Calendar;
import java.util.Date;

import static com.stevancorre.cda.scraper.utils.Parsing.parsePrice;
import static com.stevancorre.cda.scraper.utils.Parsing.parseYear;

/**
 * Utils class made to make scraping easier
 */
public final class Scraping {
    private Scraping() {
    }

    /**
     * If `element` is not null, return its content parsed as a price. Otherwise 0
     *
     * @param element The target element
     * @return The parsed price or 0
     */
    public static double scrapPrice(final HtmlElement element) {
        if (element == null) return 0;

        return parsePrice(element.getTextContent());
    }

    /**
     * If `element` is not null, returns its content. Otherwise "No description"
     *
     * @param element The target element
     * @return The element's content or "No description"
     */
    public static String scrapDescription(final HtmlElement element) {
        if (element == null) return "No description";

        return element.getTextContent();
    }

    /**
     * If `element` is not null, returns its content parsed as a year. Otherwise null
     *
     * @param element The target element
     * @return The parsed year or null
     */
    public static Integer scrapYear(final HtmlElement element) {
        if (element == null) return null;

        return parseYear(element.getTextContent());
    }
}
