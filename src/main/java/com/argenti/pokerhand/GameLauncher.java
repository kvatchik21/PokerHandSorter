package com.argenti.pokerhand;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class GameLauncher {
	static Logger log = Logger.getLogger(GameLauncher.class);

	public static void main(String[] args) throws IOException {
		String lineOfCards = null;
		int player1Count = 0;
		int player2Count = 0;

		BufferedReader streamOfHandsReader = new BufferedReader(new FileReader(args[0]));
		
		while ((lineOfCards = streamOfHandsReader.readLine()) != null) {
			Integer winner = GameServer.playOneGame(lineOfCards);

			if (winner == GameServer.PLAYER_1) {
				player1Count++;
			}
			if (winner == GameServer.PLAYER_2) {
				player2Count++;
			}
		}

		System.out.println("Player 1: " + player1Count + " hands");
		System.out.println("Player 2: " + player2Count + " hands");
	}

}
