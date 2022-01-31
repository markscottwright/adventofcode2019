package com.markscottwright.adventofcode2019;

import static java.lang.System.arraycopy;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day22 {

	public static class DeckPosition {
		long position;
		private long deckSize;

		@Override
		public String toString() {
			return "DeckPosition [position=" + position + ", deckSize=" + deckSize + "]";
		}

		public DeckPosition(long position, long deckSize) {
			this.position = position;
			this.deckSize = deckSize;
		}

		public DeckPosition reverseApply(List<Instruction> instructions) {
			var reversedInstructions = new ArrayList<Instruction>(instructions);
			Collections.reverse(reversedInstructions);

			DeckPosition position = this;
			for (Instruction instruction : reversedInstructions) {
				position = instruction.reverseApply(position);
			}
			return position;
		}

		public DeckPosition reverseApply(Cut cut) {
			cut = cut.normalize(deckSize);
			if (position < deckSize - cut.numCards)
				position += cut.numCards;
			else
				position -= deckSize - cut.numCards;
			return this;
		}

		public DeckPosition reverseApply(DealIntoNewStack dealIntoNewStack) {
			position = (deckSize - 1) - position;
			return this;
		}

		public DeckPosition reverseApply(DealWithIncrement dealWithIncrement) {
			var p = position;
			while (p % dealWithIncrement.increment != 0) {
				p += deckSize;
			}
			position = p / dealWithIncrement.increment % deckSize;
			return this;
		}
	}

	static public class Card {
		@Override
		public String toString() {
			return "Card [number=" + number + ", position=" + position + ", deckSize=" + deckSize + "]";
		}

		long position;
		long number;
		long deckSize;

		public Card(long position, long deckSize) {
			this.number = this.position = position;
			this.deckSize = deckSize;
		}

		public Card apply(Cut cut) {
			cut = cut.normalize(deckSize);
			if (position < cut.numCards)
				position += deckSize - cut.numCards;
			else
				position -= cut.numCards;
			return this;
		}

		public Card apply(DealIntoNewStack instruction) {
			position = (deckSize - 1) - position;
			return this;
		}

		public Card apply(DealWithIncrement dealWithIncrement) {
			position = position * dealWithIncrement.increment % deckSize;
			return this;
		}

		public Card apply(List<Instruction> instructions) {
			Card card = this;
			for (Instruction instruction : instructions) {
				card = instruction.apply(card);
			}
			return card;
		}

	}

	public static class Cut extends Instruction {
		@Override
		public String toString() {
			return "Cut [numCards=" + numCards + "]";
		}

		public Cut normalize(long deckSize) {
			if (numCards >= 0)
				return this;

			return new Cut(deckSize + numCards);
		}

		private long numCards;

		public Cut(long numCards) {
			this.numCards = numCards;
		}

		@Override
		Deck apply(Deck deck) {
			return deck.apply(this);
		}

		@Override
		Card apply(Card card) {
			return card.apply(this);
		}

		@Override
		DeckPosition reverseApply(DeckPosition position) {
			return position.reverseApply(this);
		}
	}

	public static class DealIntoNewStack extends Instruction {

		@Override
		public String toString() {
			return "DealIntoNewStack []";
		}

		@Override
		Deck apply(Deck deck) {
			return deck.apply(this);
		}

		@Override
		Card apply(Card card) {
			return card.apply(this);

		}

		@Override
		DeckPosition reverseApply(DeckPosition position) {
			return position.reverseApply(this);
		}

	}

	public static class DealWithIncrement extends Instruction {
		@Override
		public String toString() {
			return "DealWithIncrement [increment=" + increment + "]";
		}

		private long increment;

		public DealWithIncrement(long increment) {
			this.increment = increment;
		}

		@Override
		Deck apply(Deck deck) {
			return deck.apply(this);
		}

		@Override
		Card apply(Card card) {
			return card.apply(this);
		}

		@Override
		DeckPosition reverseApply(DeckPosition position) {
			return position.reverseApply(this);
		}

	}

	public static class Deck {
		@Override
		public String toString() {
			return "Deck [cards=" + Arrays.toString(cards) + "]";
		}

		long[] cards;

		public Deck(long size) {
			cards = new long[(int) size];
			for (long i = 0; i < size; ++i)
				cards[(int) i] = i;
		}

		public Deck(long[] newCards) {
			cards = newCards;
		}

		public Deck apply(DealIntoNewStack dealIntoNewStack) {
			Util.reverseInPlace(cards);
			return this;
		}

		public Deck apply(Cut cut) {
			if (cut.numCards > 0) {
				var newCards = new long[cards.length];
				arraycopy(cards, (int) cut.numCards, newCards, 0, (int) (cards.length - cut.numCards));
				arraycopy(cards, 0, newCards, (int) (cards.length - cut.numCards), (int) (cut.numCards));
				cards = newCards;
				return this;
			} else {
				var newCards = new long[cards.length];
				arraycopy(cards, (int) (cards.length + cut.numCards), newCards, 0, (int) -cut.numCards);
				arraycopy(cards, 0, newCards, (int) -cut.numCards, (int) (cards.length + cut.numCards));
				cards = newCards;
				return this;
			}
		}

		public Deck apply(DealWithIncrement dealWithIncrement) {
			var newCards = new long[cards.length];
			int pos = 0;
			for (int i = 0; i < cards.length; ++i) {
				newCards[pos] = cards[i];
				pos += dealWithIncrement.increment;
				if (pos >= cards.length)
					pos -= cards.length;
			}
			cards = newCards;
			return this;
		}

		public Deck apply(List<Instruction> instructions) {
			Deck deck = this;
			for (Instruction instruction : instructions) {
				deck = instruction.apply(deck);
			}
			return deck;
		}

		public long positionOfCard(long cardNumber) {
			for (int i = 0; i < cards.length; ++i)
				if (cards[i] == cardNumber)
					return i;

			throw new RuntimeException(cardNumber + " not found");
		}
	}

	public static abstract class Instruction {
		abstract Deck apply(Deck deck);

		abstract DeckPosition reverseApply(DeckPosition position);

		abstract Card apply(Card card);

		static Instruction parse(String l) {
			if (l.startsWith("deal with increment")) {
				return new DealWithIncrement(Integer.parseInt(l.split(" ")[3]));
			} else if (l.startsWith("deal into new stack")) {
				return new DealIntoNewStack();
			} else {
				return new Cut(Integer.parseInt(l.split(" ")[1]));
			}
		}
	}

	public static void main(String[] args) {
		var instructionLines = Util.getInputLines("day22.txt");
		var instructions = stream(instructionLines).map(Instruction::parse).collect(toList());
		Deck deck = new Deck(10007);
		deck = deck.apply(instructions);
		System.out.println("Day 22 Part 1: " + deck.positionOfCard(2019));

//		Deck deck2 = new Deck(10007);
//		for (int i = 0; i < 10000; ++i) {
//			deck2 = deck2.apply(instructions);
//			System.out.print(deck2.cards[2020] + " - ");
//			System.out.println(Arrays.toString(Arrays.copyOf(deck2.cards, 100)));
//		}
//
//		DeckPosition position = new DeckPosition(2020L, 101741582076661L);
//		for (int i = 0; i < 1; ++i) {
//			long current = position.position;
//			position = position.reverseApply(instructions);
//			System.out.println(
//					String.format("%20d %20d %20d", current, position.position, (position.position - current)));
//		}
	}
}
