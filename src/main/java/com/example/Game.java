package com.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
  private static Game instance;
  private final List<Player> players = new ArrayList<>();

  static Game getInstance() {
    if (instance == null) instance = new Game();
    return instance;
  }

  void add(String player) {
    if (players.size() >= 2) throw new UnsupportedOperationException("only two players allowed");
    players.add(new Player(player));
  }

  public List<Player> getPlayers() {
    return players;
  }
}
