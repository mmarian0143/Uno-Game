package com.marian.services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class UnoGameAPI { //transforming info to API & taking this information and sending it to the UI

	private static HashMap<String, UnoGameAPI> games = new HashMap<>();
	private ArrayList<Hand> hands = new ArrayList<Hand>();
	private Deck deck = new Deck();
	private int nextPlayer = 0;
	private boolean reverse = false;
	private int currentPlayer;
	private boolean winner = false;
	private Cards cardPlayed;
	private int turns = 0;
	private Cards topDiscard;

	private UnoGameAPI() {
		// game initialization code
		Deck deck = prepTheDeck();

		// Establishing four players

		hands.add(new Hand());
		hands.add(new Hand());
		hands.add(new Hand());
		hands.add(new Hand());

		// adding cards into the hands of the players
		for (int i = 0; i < 7; i++) {
			for (Hand hand : hands) {
				hand.drawCard(deck.dealCard());
			}
		}

		// add a card to the discard to start it, draw until there is a normal card
		deck.discardPile();

	}


	private Deck prepTheDeck() {
		// Prepping the deck

		deck.populate();
		System.out.println(deck);
		deck.shuffle();
		System.out.println(deck);
		return deck;
	}

	// Factory method
	public static UnoGameAPI getGame(String gameName) {
		// use 'gameName' as a key in the games HashMap
		// if 'gameName' is not found, create a new game and add it to 'games' HashMap.
		// return the new game.
		// if 'gameName' is found, return that game.
		UnoGameAPI game = games.get(gameName);
		if (game == null) {
			game = new UnoGameAPI();
			games.put(gameName, game);
		}
		return game;
	}

	public boolean nextTurn() {
		// returns true as long as a winner has not been declared
		// returns false when the game has a winner
		// once winner has been determined, this method should return false each time.
		if (winner == false) { // loops through players until there is a winner
			turns++;
			deck.replenish(); // checks if deck needs to be replenished
			topDiscard = deck.topDiscard(); // getting the top card on the discard deck
			currentPlayer = nextPlayer;
			System.out.printf("Turn: %d\n", turns);
			System.out.printf("Top Card: %s\n", topDiscard);
			System.out.printf("Player: %d %s \n", currentPlayer, hands.get(currentPlayer));
			cardPlayed = hands.get(currentPlayer).hasMatch(topDiscard); // checking if player has a match
			if (cardPlayed != null) { // if the player can play a card
				System.out.println("Card Played:" + cardPlayed); // what card they placed down
				if (cardPlayed.isSpecial(cardPlayed) == true) { // checks if card is special
					switch (cardPlayed.getValue()) {
					case SKIP:
						nextPlayer = getNextPlayer(nextPlayer);
						break;
					case REVERSE:
						reverse = !reverse;
						break;
					case DRAWTWO:
						int drawPlayer = getNextPlayer(nextPlayer);
						for (int x = 0; x < 2; x++) {
							hands.get(drawPlayer).drawCard(deck.dealCard());
						}
						break;
					case WILD:
						hands.get(currentPlayer).colorCount(cardPlayed);
						System.out.println("Player has called the color: " + cardPlayed.getColor());
						break;
					case WILD_DRAWFOUR:
						hands.get(currentPlayer).colorCount(cardPlayed);
						System.out.println("Player has called the color: " + cardPlayed.getColor());
						int wildNextPlayer = getNextPlayer(nextPlayer);
						for (int x = 0; x < 4; x++) {
							hands.get(wildNextPlayer).drawCard(deck.dealCard());
						}
						break;
					default:
						System.out.println("Hmm something went wrong with a special card");
						break;

					}
					System.out.println("A special card has been played");
				}

				deck.addToDiscard(cardPlayed); // takes the card the player played and puts it at the top of the discard
												// deck
				if (hands.get(currentPlayer).isUno() == true) { // checks if player can call Uno
					System.out.printf("\nPlayer %d calls UNO!\n", currentPlayer);
				}
				if (hands.get(currentPlayer).isWinner() == true) { // checks if player won
					winner = true;
					System.out.printf("\nPlayer %d Won\n", currentPlayer);
				}
			} else { // if the player can't match a card they pick one up
				hands.get(currentPlayer).drawCard(deck.dealCard()); // player draws one card
				System.out.printf("Draw Card: %d \n", nextPlayer); // display player number
				System.out.println(hands.get(currentPlayer)); // displays current players hand
			}
			if (!winner) {
				nextPlayer = getNextPlayer(nextPlayer); // getting the next player
				System.out.println();
			}
			return true;
		} else {
			return false;
		}
	}

	public Turn getTurn() { //every time we call getTurn, we are going to construct a turn on demand and send it to the caller
		
		if (topDiscard == null) {
			topDiscard = deck.getDiscard().get(0);
		}
		
		return new Turn(hands, deck, nextPlayer, reverse, currentPlayer, winner,
				cardPlayed, turns, topDiscard);
		// returns current turn
		// once the winner is declared, should return the last turn of the game
		
	}
	public static String[] getGameKeys() {
		
		Set<String> keys = games.keySet();
		return keys.toArray(new String[0]);
		
	}
	public static boolean hasGameKey(String key) {
		 return games.get(key) != null; 
		
	} 

	private int getNextPlayer(int i) {

		if (reverse == false) {
			i++;
		} else if (reverse == true) {
			i--;
		}
		if (i > 3) { // making sure that i does not break out of array
			i = 0;
		}
		if (i < 0) { // making sure that i does not break out of array
			i = 3;
		}
		return i;
	}

}
