package com.apilivros.Domain.enums;

public enum Genre {

    FANTASY(1, "Fantasy"),
    HORROR(2, "Horror"),
    THRILLER(3, "Thriller"),
    SCIENCE_FICTION(4, "Science Fiction"),
    ROMANCE(5, "Romance"),
    ADVENTURE(6, "Adventure"),
    HISTORY(7, "History"),
    BIOGRAPHY(8, "Biography"),
    POETRY(9, "Poetry"),
    PHILOSOPHY(10, "Philosophy"),
    CHILDRENS(11, "Children's"),
    YOUNG_ADULT(12, "Young Adult"),
    DYSTOPIA(13, "Dystopia"),
    GRAPHIC_NOVEL(14, "Graphic Novel"),
    SHORT_STORY(15, "Short Story"),
    PARANORMAL(16, "Paranormal"),
    SPORTS(17, "Sports"),
    TRAVEL(18, "Travel");

    private final int code;
    private final String name;

    Genre(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Genre fromCode(int code) {
        for (Genre genre : values()) {
            if (genre.getCode() == code) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid Genre code: " + code);
    }

    public static Genre fromName(String name) {
        for (Genre genre : values()) {
            if (genre.getName().equalsIgnoreCase(name)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Invalid Genre name: " + name);
    }
}
