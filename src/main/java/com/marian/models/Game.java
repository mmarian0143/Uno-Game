package com.marian.models;

import org.springframework.stereotype.Component;

@Component
public class Game {

	private String gameId;

	public String getGameId() {
		return gameId;
	}

	public Game() {

	}

	public Game(String gameID) {
		super();
		this.gameId = gameID;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + "]";
	}

}
