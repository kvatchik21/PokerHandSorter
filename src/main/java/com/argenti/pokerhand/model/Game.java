package com.argenti.pokerhand.model;

public class Game {
	private Hand[] hands;

	public Game(Hand hand1, Hand hand2) {
		this.hands = new Hand[2];
		this.hands[0] = hand1;
		this.hands[1] = hand2;
	}

	/*
	 * AH 9S 4D TD 8S 4H JS 3C TC 8D 
	 * |--Player 1--| |--Player 2--|
	 * 
	 */
	public static Game newGameTwoPlayers(String initHands) {
		Hand hand1 = new Hand();
		Hand hand2 = new Hand();

		hand1.getCards().add(new Card(initHands.substring(0, 2)));
		hand1.getCards().add(new Card(initHands.substring(3, 5)));
		hand1.getCards().add(new Card(initHands.substring(6, 8)));
		hand1.getCards().add(new Card(initHands.substring(9, 11)));
		hand1.getCards().add(new Card(initHands.substring(12, 14)));

		hand2.getCards().add(new Card(initHands.substring(15, 17)));
		hand2.getCards().add(new Card(initHands.substring(18, 20)));
		hand2.getCards().add(new Card(initHands.substring(21, 23)));
		hand2.getCards().add(new Card(initHands.substring(24, 26)));
		hand2.getCards().add(new Card(initHands.substring(27, 29)));

		return new Game(hand1, hand2);
	}

	public Hand getHand(int player) {
		return hands[player];
	}

}
