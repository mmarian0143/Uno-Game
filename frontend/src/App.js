import React from "react";
import { Button } from "react-bootstrap";
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";

import ShowCard from "./gamePlay/ShowCard";
import ShowHand from "./gamePlay/ShowHand";

class App extends React.Component {
  initialGameState = {
    initial: true,
    buttonText: "Start Game"
  };

  constructor(props) {
    super(props);
    this.state = this.initialGameState;
  }

  gameId = "Demo";
  startGame = () => {
    fetch("/games", {
      method: (this.state.initial ? "POST" : "PUT"),
      body: JSON.stringify({
        gameId: this.gameId,
      }),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((data) => {
        let d = data.json();
        return d;
      })
      .then((game) => {
        this.setState({ // Call setState to create new state 
          initial: false, 
          game: game,
          gameId: this.gameId,
          buttonText: "Next Turn",
        });
      });
  };

  // restartGame = () => {
  //   fetch("http://localhost:8080/games"), {
  //     method: "DELETE",
  //     body: JSON.stringify({
  //       gameId: this.gameId,
  //     }),
  //     headers: {
  //       "Content-Type": "application/json",
  //     },
  //   });
  //     .then((data) => {
  //       let d = data.json();
  //       return d;
  //     })
  //     .then((game) => {
  //       this.setState(this.initialGameState);
  //       this.startGame();
  //     });
  // };

  render() {
    let pages = [];
    let playerNames = ["Dez", "Allen", "Madalina", "Matt"];
    if (!this.state.initial) {
      for (let i = 0; i < this.state.game.hands.length; i++) {
        pages.push( // Pushes JSX 'ShowHand'
          <ShowHand
            key={i}
            initial={this.state.initial}
            hand={
              this.state.initial ? {} : Object.assign({}, this.state.game.hands[i])
            }
            playerNumber={i}
            playerName={playerNames[i]}
          
          ></ShowHand>
        );
      }
    }

    return (
      <div className="App">
        <header className="App-header">
          <ShowCard
            initial={this.state.initial}
            topDiscard={
              this.state.initial ? {} : Object.assign({}, this.state.game.cardPlayed ? this.state.game.cardPlayed : this.state.game.topDiscard) //Truthy expression, if cardPlayed active can use it, otherwise use previous topcard
            }
          ></ShowCard>
          {pages}
          <Button variant="success" onClick={this.startGame}>{this.state.buttonText}</Button>{" "}
          {/* <Button variant="dark" onClick={this.restartGame}>Restart Game</Button>{" "} */}
        </header>
      </div>
    );
  }
}
export default App;
