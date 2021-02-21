package com.example.model;

import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Game {
  public static final int MAX_PLAYERS = 2;
  private final List<Player> players;
  private final String name;
  private final Map<Player, Action> playerToAction = new HashMap<>();
  private Player winner;

  public void add(String player) {
    if (players.size() >= MAX_PLAYERS) {
      throw new UnsupportedOperationException("only two players allowed");
    }
    players.add(new Player(player));
  }

  public void setWinner(Player player) {
    winner = player;
  }
}
