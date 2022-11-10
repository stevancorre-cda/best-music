package com.stevancorre.cda.scraper.providers.abstraction;

/**
 * Represents a genre
 */
public enum Genre {
    Blues(1),
    Jazz(2),
    Reggae(3),
    Funk(4),
    Electro(5),
    DubStep(6),
    Rock(7),
    Saoul(8);

    private final int id;

    Genre(final int value) {
        this.id = value;
    }

    /**
     * Get the associated id
     *
     * @return The genre's id
     */
    public int getId() {
        return id;
    }
}
