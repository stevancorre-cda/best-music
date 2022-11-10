package com.stevancorre.cda.scraper.utils;

/**
 * Utils class made to make parsing easier
 */
public final class Parsing {
    private Parsing() {
    }

    /**
     * Parse an input string as a price (handles "Don", and any format: 14,0€ €47...)
     *
     * @param source The source string
     * @return The parsed price
     */
    public static double parsePrice(final String source) {
        // if source is "don", return 0
        if (source.toLowerCase().contains("don")) return 0;

        // replace comma by dot
        // then remove everything that isn't a digit
        return Double.parseDouble(source
                .replace(',', '.')
                .replaceAll("[^0-9.]", "")
                .trim());
    }

    /**
     * Parse an input string as a year
     *
     * @param source The source string
     * @return The parsed year
     */
    public static Integer parseYear(final String source) {
        try {
            // then remove everything that isn't a digit
            final String rawYear = source
                    .replaceAll("[^0-9]", "")
                    .trim();
            return Integer.parseInt(rawYear);
        } catch (final NumberFormatException ignored) {
            return null;
        }
    }
}
