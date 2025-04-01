package com.apilivros.Domain.enums;

public enum Genre {
    FANTASY(1),
    HORROR(2),
    THRILLER(3),
    SCI_FI(4),
    NOVEL(5),
    FAIRY_TALE(6),
    SATIRE(7),
    MISTERY(8),
    DRAMA(9),
    ROMANCE(10),
    ADVENTURE(11),
    BIOGRAPHY(12),
    HISTORICAL(13),
    POETRY(14),
    SELF_HELP(15),
    PHILOSOPHY(16),
    AUTOBIOGRAPHY(17),
    CRIME(18),
    MYTHOLOGY(19),
    HUMOR(20),
    RELIGION(21),
    CLASSIC(22),
    ESSAY(23),
    SCIENCE(24),
    CHILDRENS(25),
    YOUNG_ADULT(26),
    DYSTOPIA(27),
    GRAPHIC_NOVEL(28),
    SHORT_STORY(29),
    PARANORMAL(30),
    SPORTS(31),
    TRAVEL(32);

    private int code;

    private Genre(int code) {
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static Genre valueOf(int code){
        for(Genre value : Genre.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Codigo invalido!");
    }
}
