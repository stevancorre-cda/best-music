package com.stevancorre.cda.scraper.utils;
/**
 * Utils class made to make formatting easier
 */
public final class Formatting {
    private Formatting() {
    }

    /**
     * Format a query by replacing all spaces by a specific string
     *
     * @param query The source string
     * @param spaceReplacement String used to replace spaces in the source
     * @return The formatted query
     */
    public static String formatQuery(final String query, final String spaceReplacement) {
        return query.replace(" ", spaceReplacement);
    }

    /**
     * Format a string by replacing all extra spaces in it (trim, remove new lines, tabs and double spaces)
     *
     * @param source The source string
     * @return The formatted string
     */
    public static String removeExtraSpaces(final String source) {
        return source
                .trim()
                .replace("\n", "")
                .replaceAll(" +|\t", " ");
    }
}
