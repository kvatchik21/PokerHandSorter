package com.argenti.pokerhand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.argenti.pokerhand.model.Card;
import com.argenti.pokerhand.model.Combination;
import com.argenti.pokerhand.model.CombinationTypeEnum;
import com.argenti.pokerhand.model.Hand;
import com.argenti.pokerhand.model.CardSuitEnum;
import com.argenti.pokerhand.model.CardValueEnum;

import static com.argenti.pokerhand.model.CardValueEnum.*;
import static com.argenti.pokerhand.model.CombinationTypeEnum.*;

public class CombinationHelper {
	static Logger log = Logger.getLogger(CombinationHelper.class);
	
	private static final CardValueEnum[] ROYAL_FLUSH_VALUES = { ACE, KING, QUEEN, JACK, TEN };
	private static final CombinationTypeEnum[] PAIR_AND_THREE_OF_KIND = { PAIR, THREE_OF_A_KIND };
	private static final CombinationTypeEnum[] PAIR_AND_PAIR = { PAIR, PAIR };

	public static List<Combination> findAllCombinations(Hand hand) {
		List<Combination> result = new ArrayList<Combination>();
		
		findCombinationsStraight(hand, result);
		findCombinationsHightCard(hand, result);
		findCombinationsRepeatingCards(hand, result);
		findCombinationsLast(hand, result);
		
		return result.stream().sorted(sortByRankAndValue.reversed()).collect(Collectors.toList());
	}

	private static void findCombinationsStraight(Hand hand, List<Combination> combinations) {
		CardValueEnum straightValue = getStraightCardValue(hand);
		boolean isStraight = straightValue != null ? true : false; 

		if (isStraight) {
			combinations.add(new Combination(STRAIGHT, straightValue));
			
			if (isHandAllSameSuit(hand)) {
				combinations.add(new Combination(STRAIGHT_FLUSH, straightValue));
			}
			
			if (isHandContainAllCardValues(hand, ROYAL_FLUSH_VALUES)) {
				combinations.add(new Combination(ROYAL_FLUSH, straightValue));
			}
		}
	}

	private static void findCombinationsHightCard(Hand hand, List<Combination> combinations) {
		combinations.addAll(hand.getCards().stream()
				.map(cmb -> new Combination(HIGH_CARD, cmb.getCardValue()))
				.collect(Collectors.toList()));
	}

	private static void findCombinationsRepeatingCards(Hand hand, List<Combination> combinations) {
		Map<CardValueEnum, List<Card>> cardsPerValue = hand.getCards().stream()
				.collect(Collectors.groupingBy(Card::getCardValue));

		cardsPerValue.forEach((key, value) -> {
			CardValueEnum firstValue = value.get(0).getCardValue();
			
			if (value.size() == 2) {
				combinations.add(new Combination(PAIR, firstValue));
			}
			if (value.size() == 3) {
				combinations.add(new Combination(THREE_OF_A_KIND, firstValue));
			}
			if (value.size() == 4) {
				combinations.add(new Combination(FOUR_OF_A_KIND, firstValue));
			}
		});
	}

	private static void findCombinationsLast(Hand hand, List<Combination> combinations) {
		boolean isContainPairAndThreeOfKind = isCombinationsContain(combinations, PAIR_AND_THREE_OF_KIND);
		if (isContainPairAndThreeOfKind) {
			Combination threeOfKindCombination = getCombinationValue(combinations, THREE_OF_A_KIND);
			combinations.add(new Combination(FULL_HOUSE, threeOfKindCombination.getCardValue())); 
		}

		boolean isContainTwoPairs = isCombinationsContain(combinations, PAIR_AND_PAIR);
		if (isContainTwoPairs) {
			Combination twoPairsCombination = getCombinationValue(combinations, PAIR);
			combinations.add(new Combination(TWO_PAIRS, twoPairsCombination.getCardValue()));
		}
		
		if (isHandAllSameSuit(hand)) {
			Combination flashCombination = getCombinationValue(combinations, HIGH_CARD);
			combinations.add(new Combination(FLUSH, flashCombination.getCardValue()));
		}
	}

	private static Combination getCombinationValue(List<Combination> combinations, CombinationTypeEnum combinationType) {
		Optional<Combination> result = combinations.stream()
				.filter(cmb -> cmb.getCombination() == combinationType)
		        .findFirst();

		return result.orElse(null);
	}

	private static boolean isHandAllSameSuit(Hand hand) {
		Map<CardSuitEnum, List<Card>> cardsPerSuit = hand.getCards().stream()
				.collect(Collectors.groupingBy(Card::getCardSuit));
		
		return cardsPerSuit.size() == 1;
	}

	private static boolean isCombinationsContain(List<Combination> combinations, CombinationTypeEnum[] combinationTypes) {
		List<CombinationTypeEnum> combinationTypesToFind = Arrays.asList(combinationTypes);
		
		List<CombinationTypeEnum> combinationTypesFound = combinations.stream()
				.map(cmb -> cmb.getCombination())
				.collect(Collectors.toList());

		long counter = combinations.stream()
				.filter(cmb -> combinationTypesToFind.contains(cmb.getCombination()))
				.count();
	
		return combinationTypesFound.containsAll(combinationTypesToFind) 
				&& counter == combinationTypesToFind.size();
	}
	
	private static boolean isHandContainAllCardValues(Hand hand, CardValueEnum[] values) {
		List<CardValueEnum> cardValues = hand.getCards().stream()
				.map(c -> c.getCardValue())
				.collect(Collectors.toList());
		
		return cardValues.containsAll(Arrays.asList(values));
	}

	public static int compareCombinations(List<Combination> combinationsList1, List<Combination> combinationsList2) {
		int longestListSize = Math.max(combinationsList1.size(), combinationsList2.size()); 
		
		for (int i = 0; i <= longestListSize; i++) {
			Combination cmb1 = getNextBestCombination(combinationsList1, i);
			Combination cmb2 = getNextBestCombination(combinationsList2, i);
			log.debug("Comparing combinations: " + cmb1 + " vs " + cmb2);
			
			if (cmb1 == null && cmb2 == null) {
				return 0;
			}
			if (cmb1 == null) {
				return -1;
			}
			if (cmb2 == null) {
				return 1;
			}
			
			int result = sortByRankAndValue.compare(cmb1, cmb2);
			if (result != 0 ) {
				return result;
			}
		}
		return 0;
	}

	private static Combination getNextBestCombination(List<Combination> combinations, int nextBest) {
		if (nextBest >= combinations.size()) {
			return null;
		}
		return combinations.get(nextBest);
	}

	private static CardValueEnum getStraightCardValue(Hand hand) {
		boolean isStraight = false;
		CardValueEnum previousCardValue = null;

		for (Card card : hand.getCards().stream()
				.sorted((c1, c2) -> c1.getCardValue().getValue().compareTo(c2.getCardValue().getValue()))
				.collect(Collectors.toList())) {
			
			if (previousCardValue == null) {
				isStraight = true;

			} else if (card.getCardValue().getValue() != previousCardValue.getValue() + 1) {
				isStraight = false;
				break;
			}
			previousCardValue = card.getCardValue();
		}
		
		if (isStraight) {
			return previousCardValue;
		}
		
		return null;
	}
	
	private static Comparator<Combination> sortByRankAndValue = (cmb1, cmb2) -> {
		int rank1 = cmb1.getCombination().getRank();
		int rank2 = cmb2.getCombination().getRank();
		
		int value1 = cmb1.getCardValue().getValue();
		int value2 = cmb2.getCardValue().getValue();

		if (rank1 == rank2) {
			return value1 - value2;
		}

		return rank1 - rank2;
	};

}
