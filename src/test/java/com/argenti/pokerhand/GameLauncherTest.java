package com.argenti.pokerhand;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class GameLauncherTest extends TestCase {
	static Logger log = Logger.getLogger(GameLauncherTest.class);
	
	public void testCombinations_TwoPairs_HightCard() {
		Integer winner = GameServer.playOneGame("AH 3H 4H 5H 6D JS JS 3C 4C 4D");
		assertEquals(GameServer.PLAYER_2, winner);
	}
	
	public void testCombinations_RoyalFlash_TwoPaid() {
		Integer winner = GameServer.playOneGame("AH KH QH JH TH JS JS 3C 4C 4D");
		assertEquals(GameServer.PLAYER_1, winner);
	}

	public void testCombinations_HightCard_HightCard() {
		Integer winner = GameServer.playOneGame("AH 3H 4H 5H 6D KS QS JC 3C 4D");
		assertEquals(GameServer.PLAYER_1, winner);
	}

	public void testCombinations_FullHouse_TwoPairs() {
		Integer winner = GameServer.playOneGame("KH QH JH 5H 6D KS QS JC 6C 7D");
		assertEquals(GameServer.PLAYER_2, winner);
	}

	public void testCombinations_HightCard_HightCard_tie() {
		Integer winner = GameServer.playOneGame("AH 3H 4H 5H 6D AD 3D 4D 5D 6H");
		assertNull(winner);	
	}

}
