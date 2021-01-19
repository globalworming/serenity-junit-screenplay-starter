package com.example;

public class PlayerService {

  public void joinGame(String player) {
    Game game = Game.getInstance();
    game.add(player);
  }
}
