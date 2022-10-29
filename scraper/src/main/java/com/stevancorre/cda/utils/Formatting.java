package com.stevancorre.cda.utils;

public final class Formatting {
    private Formatting() {
    }

    public static String formatQuery(final String query, final String spaceReplacement) {
        return query.replace(" ", spaceReplacement);
    }
}
