package com.argenti.pokerhand.model;

public class Card {
	private CardSuitEnum cardSuit;
	private CardValueEnum cardValue;
	
    public Card(String card) {
    	this.cardValue = CardValueEnum.valueOfLabel(card.substring(0,1));
    	this.cardSuit = CardSuitEnum.valueOfLabel(card.substring(1,2));
    }

    public Card(CardSuitEnum suits, CardValueEnum value) {
        this.cardSuit = suits;
        this.cardValue = value;
    }

    public CardSuitEnum getCardSuits() {
        return cardSuit;
    }

    public CardValueEnum getCardValue() {
        return cardValue;
    }

    public boolean valueEquals(CardValueEnum v) {
        return cardValue.getValue().equals(v.getValue());
    }

	public CardSuitEnum getCardSuit() {
		return cardSuit;
	}

	public void setCardSuit(CardSuitEnum suit) {
		this.cardSuit = suit;
	}

}
