package com.argenti.pokerhand.model;

public enum CardValueEnum {

    TWO("2",2),
    THREE("3",3),
    FOUR("4",4),
    FIVE("5",5),
    SIX("6",6),
    SEVEN("7",7),
    EIGHT("8",8),
    NINE("9",9),
    TEN("T",10),
    JACK("J",11),
    QUEEN("Q",12),
    KING("K",13),
    ACE("A",14);

    private final String name;
    private final Integer value;

    CardValueEnum(final String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public static CardValueEnum valueOfLabel(String label) {
        for (CardValueEnum e : values()) {
            if (e.name.equals(label)) {
                return e;
            }
        }
        return null;
    }

}
