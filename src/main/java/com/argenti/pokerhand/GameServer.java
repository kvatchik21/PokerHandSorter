package com.argenti.pokerhand;

import java.util.List;

import org.apache.log4j.Logger;

import com.argenti.pokerhand.model.Combination;
import com.argenti.pokerhand.model.Game;

public class GameServer {
	static Logger log = Logger.getLogger(GameServer.class);

	public final static Integer PLAYER_1 = 0;
	public final static Integer PLAYER_2 = 1;
	public final static String[] PLAYER_NAMES = { "Player1", "Player2" };

	public static Integer playOneGame(String initCards) {
		log.debug("New Game " + initCards);
		
		Integer winner = playOneGameTwoPlayers(initCards);
		
		log.debug("The winner is " + getWinnerName(winner));
		return winner;
	}

	private static Integer playOneGameTwoPlayers(String initCards) {
		Game game = Game.newGameTwoPlayers(initCards);

		List<Combination> player1Combinations = CombinationHelper.findAllCombinations(game.getHand(PLAYER_1));
		List<Combination> player2Combinations = CombinationHelper.findAllCombinations(game.getHand(PLAYER_2));

		log.debug("Player1 ranked combinations:");
		logCombinations(player1Combinations);
		log.debug("Player2 ranked combinations:");
		logCombinations(player2Combinations);
		
		int result = CombinationHelper.compareCombinations(player1Combinations, player2Combinations);
		
		if (result > 0) {
			return PLAYER_1;
			
		} else if (result < 0) {
			return PLAYER_2;
		} 
		
		return null;
	}

	private static void logCombinations(List<Combination> combinations) {
		combinations.stream().forEach(cmb -> log.debug(cmb));
	}

	private static String getWinnerName(Integer winner) {
		return (winner != null) ? PLAYER_NAMES[winner] : "Draw";
	}

}
