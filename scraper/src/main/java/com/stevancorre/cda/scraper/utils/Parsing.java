package com.stevancorre.cda.scraper.utils;

public final class Parsing {
    private Parsing() {
    }

    public static double parsePrice(final String source) {
        if (source.toLowerCase().contains("don")) return 0;

        return Double.parseDouble(source
                .replace(',', '.')
                .replaceAll("[^0-9.]", "")
                .trim());
    }

    public static Integer parseYear(final String source) {
        try {
            final String rawYear = source
                    .replaceAll("[^0-9]", "")
                    .trim();
            return Integer.parseInt(rawYear);
        }
        catch (final NumberFormatException ignored) {
            return null;
        }
    }
}
