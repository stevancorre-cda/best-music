package com.stevancorre.cda.utils;

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
}
