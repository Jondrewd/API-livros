package com.apilivros.Domain.enums;

public enum Genre {
    FANTASY(1),
    HORROR(2),
    THRILLER(3),
    SCI_FI(4),
    NOVEL(5),
    FAIRY_TALE(6),
    SATIRE(7),
    MISTERY(8);

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
