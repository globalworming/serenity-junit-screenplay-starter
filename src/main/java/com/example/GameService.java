package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameService {
  private static GameService instance;

  private final Map<String, Game> gameNameToGame = new HashMap<>();

  static GameService getInstance() {
    if (instance == null) {
      instance = new GameService();
    }
    return instance;
  }

  private void createGame(Game game) {
    gameNameToGame.put(game.getName(), game);
  }

  public Game getGame(String gameName) {
    return gameNameToGame.get(gameName);
  }

  public void joinGame(String gameName, String player) {
    getGame(gameName).add(player);
  }

  public void createGame(String gameName) {
    createGame(Game.builder().name(gameName).players(new ArrayList<>()).build());
  }

  public String getWinner(String gameName) {
    return getGame(gameName).getWinner().getName();
  }

  public void addPlayer(String name, String gameName) {
    getGame(gameName).add(name);
  }

  public void remove(String gameName, String playerName) {
    getGame(gameName).getPlayers().removeIf(player -> playerName.equals(player.getName()));
  }
}
