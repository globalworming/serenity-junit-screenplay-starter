package com.example;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Game {
  private final List<Player> players;
  private final String name;

  void add(String player) {
    if (players.size() >= 2) {
      throw new UnsupportedOperationException("only two players allowed");
    }
    players.add(new Player(player));
  }
}
