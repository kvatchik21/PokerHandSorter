package com.argenti.pokerhand.model;

public enum CardSuitEnum {

	DIAMONDS("D"), 
	HEARTS("H"), 
	SPADES("S"), 
	CLUBS("C");

	private final String suit;

	CardSuitEnum(final String suit) {
        this.suit = suit;
    }

	@Override
	public String toString() {
		return suit;
	}

	public static CardSuitEnum valueOfLabel(String label) {
		for (CardSuitEnum e : values()) {
			if (e.suit.equals(label)) {
				return e;
			}
		}
		return null;
	}

}
