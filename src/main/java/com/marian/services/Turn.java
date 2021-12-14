package com.marian.services;
import java.util.ArrayList;




public class Turn {
	
	private boolean winner = false;
	
	
	// Contains all the information needed for the U/I to show turn results
	private ArrayList<Hand> hands = new ArrayList<Hand>();
	private Deck deck = new Deck(); //called them in the front so we can have them available within our
	private int nextPlayer = 0;		//UnoGameAPI Object
	private boolean reverse = false;
	private int currentPlayer;
	private Cards cardPlayed;
	private int turns = 0;
	private Cards topDiscard;
	

	public ArrayList<Hand> getHands() {
		return hands;
	}

	public Deck getDeck() {
		return deck;
	}

	@Override
	public String toString() {
		return "Turn [hands=" + hands + ", deck=" + deck + ", nextPlayer=" + nextPlayer + ", reverse=" + reverse
				+ ", currentPlayer=" + currentPlayer + ", winner=" + winner + ", cardPlayed=" + cardPlayed + ", turns="
				+ turns + ", topDiscard=" + topDiscard + "]";
	}

	public int getNextPlayer() { //set getters only b/c we don't want it to be modified but instead have access to the methods
		return nextPlayer;
	}

	public boolean isReverse() {
		return reverse;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean isWinner() {
		return winner;
	}

	public Cards getCardPlayed() {
		return cardPlayed;
	}

	public int getTurns() { 
		return turns;
	}

	public Cards getTopDiscard() {
		return topDiscard;
	}


	public Turn(ArrayList<Hand> hands, Deck deck, int nextPlayer, boolean reverse, int currentPlayer, boolean winner,
			Cards cardPlayed, int turns, Cards topDiscard) {
		super();
		this.hands = hands;			//called in order to be able to read all this information
		this.deck = deck;
		this.nextPlayer = nextPlayer;
		this.reverse = reverse;
		this.currentPlayer = currentPlayer;
		this.winner = winner;
		this.cardPlayed = cardPlayed;
		this.turns = turns;
		this.topDiscard = topDiscard;
	}
	
}
