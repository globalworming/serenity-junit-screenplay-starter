package com.example;

import lombok.Builder;
import lombok.Getter;

import java.util.*;

import static com.example.Action.PAPER;
import static com.example.Action.SCISSORS;
import static com.example.Action.STONE;

@Builder
@Getter
public class Game {
  private static final int MAX_PLAYERS = 2;
  private final List<Player> players;
  private final String name;
  private final Map<Player, Action> playerToAction = new HashMap<>();
  private Player winner;

  void add(String player) {
    if (players.size() >= MAX_PLAYERS) {
      throw new UnsupportedOperationException("only two players allowed");
    }
    players.add(new Player(player));
  }

  void pickAction(String name, Action action) {
    Player player = players.stream()
        .filter(it -> it.getName().equals(name))
        .findFirst().orElseThrow(NullPointerException::new);
    playerToAction.put(player, action);
    if (allPlayersPickedAnAction()) {
      winner = determinWinner(playerToAction);
    }
  }

  private Player determinWinner(Map<Player, Action> playerToAction) {
    ArrayList<Map.Entry<Player, Action>> entries = new ArrayList<>(playerToAction.entrySet());
    return determinWinner(entries.get(0).getKey(), entries.get(0).getValue(), entries.get(1).getKey(), entries.get(1).getValue());
  }

  private Player determinWinner(Player player1, Action action1, Player player2, Action action2) {
    Action winningAction = winningAction(action1, action2);
    if (winningAction == null) {
      return null;
    }
    return winningAction.equals(action1) ? player1 : player2;
  }

  static Action winningAction(Action action1, Action action2) {
    List<Action> actions = Arrays.asList(action1, action2);
    if (action1.equals(action2)) {
      return null;
    }

    if (actions.containsAll(Arrays.asList(SCISSORS, STONE))) {
      return STONE;
    }
    if (actions.containsAll(Arrays.asList(PAPER, STONE))) {
      return PAPER;
    }
    return SCISSORS;
  }


  private boolean allPlayersPickedAnAction() {
    return playerToAction.keySet().size() == MAX_PLAYERS;
  }
}
