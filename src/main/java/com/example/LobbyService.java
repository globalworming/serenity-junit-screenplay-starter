package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LobbyService {
  private static LobbyService instance;

  private final Map<String, Game> gameNameToGame = new HashMap<>();

  static LobbyService getInstance() {
    if (instance == null) {
      instance = new LobbyService();
    }
    return instance;
  }

  void createGame(Game game) {
    gameNameToGame.put(game.getName(), game);
  }

  Game getGame(String gameName) {
    return gameNameToGame.get(gameName);
  }

  void joinGame(String gameName, String player) {
    getGame(gameName).add(player);
  }

  public void createGame(String gameName) {
    Game game = Game.builder()
        .name(gameName)
        .players(new ArrayList<>()).build();
    gameNameToGame.put(gameName, game);
  }
}
