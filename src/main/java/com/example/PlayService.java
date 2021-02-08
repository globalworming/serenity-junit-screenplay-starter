package com.example;

public class PlayService {

  private final GameService gameService = GameService.getInstance();

  public void pickAction(String gameName, String player, Action action) {
    gameService.getGame(gameName).pickAction(player, action);
  }
}
