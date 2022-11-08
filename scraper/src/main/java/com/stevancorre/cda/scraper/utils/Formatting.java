package com.stevancorre.cda.scraper.utils;

public final class Formatting {
    private Formatting() {
    }

    public static String formatQuery(final String query, final String spaceReplacement) {
        return query.replace(" ", spaceReplacement);
    }

    public static String removeExtraSpaces(final String source) {
        return source
                .trim()
                .replace("\n", "")
                .replaceAll(" +|\t", " ");
    }
}
