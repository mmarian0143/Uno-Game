package com.marian.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.marian.models.Game;
import com.marian.services.Turn;
import com.marian.services.UnoGameAPI;

@RestController
@CrossOrigin(maxAge = 3600)
public class GameController {
	@PostMapping("/games")
	Turn postGame(@RequestBody Game game) {

		System.out.println(game);

		Turn start = null;
		UnoGameAPI gameAPI = UnoGameAPI.getGame(game.getGameId()); 
		start = gameAPI.getTurn();
		return start;

		// Use the UnoGameAPI to
		// 1. Return the current game turn of an existing game.
		// 2. Or create a new game and return the first turn.
		// 3. Post should not advance the game
	}

	@GetMapping("/games")

	List<Game> getGames() {
		// Use the UnoGameAPI to
		// 1. Get all existing game names
		// 2. This is a new method for the UnoGameAPI
		String[] names = UnoGameAPI.getGameKeys();

		List<Game> games = new ArrayList<>(); // array list of games that has names inside
		for (String name : names) { 
			games.add(new Game(name)); 
		}

		return games;
	}

	@PutMapping("/games")
	Turn getTurn(@RequestBody Game game) {
		 

			UnoGameAPI gameAPI = UnoGameAPI.getGame(game.getGameId()); // passing the same gameID using the factory
																		// method to get game
																	
			gameAPI.nextTurn(); 
			Turn turn = gameAPI.getTurn(); //once you post a new gameId you'll get the new one to post 
			return turn;
		
		 
	}																						//this error will throw
}
