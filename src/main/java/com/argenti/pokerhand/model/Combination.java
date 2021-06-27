package com.argenti.pokerhand.model;

public class Combination {
	private CombinationTypeEnum combination;
	private CardValueEnum cardValue;

	public Combination(CombinationTypeEnum cmb, CardValueEnum value) {
		this.combination = cmb;
		this.cardValue = value;
	}

	public CombinationTypeEnum getCombination() {
		return combination;
	}

	public void setCombination(CombinationTypeEnum cmb) {
		this.combination = cmb;
	}

	public CardValueEnum getCardValue() {
		return cardValue;
	}

	public void setCardValue(CardValueEnum value) {
		this.cardValue = value;
	}

	@Override
	public String toString() {
		return "" + combination.name() + "(" + cardValue + ")";
		
	}
}
