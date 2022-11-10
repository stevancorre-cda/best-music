package com.stevancorre.cda.scraper.providers.abstraction;

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

    public int getId() {
        return id;
    }
}
