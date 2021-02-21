package com.example.service;

import com.example.model.Action;
import com.example.model.Game;
import com.example.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.model.Action.PAPER;
import static com.example.model.Action.SCISSORS;
import static com.example.model.Action.STONE;

public class PlayService {

  private final GameService gameService = GameService.getInstance();

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

  public void pickAction(String gameName, String player, Action action) {
    Game game = gameService.getGame(gameName);
    Player player1 =
        game.getPlayers().stream()
            .filter(it -> it.getName().equals(player))
            .findFirst()
            .orElseThrow(NullPointerException::new);
    game.getPlayerToAction().put(player1, action);
    if (allPlayersPickedAnAction(game.getPlayerToAction())) {
      game.setWinner(determineWinner(game.getPlayerToAction()));
    }
  }

  private boolean allPlayersPickedAnAction(Map<Player, Action> playerToAction) {
    return playerToAction.keySet().size() == Game.MAX_PLAYERS;
  }

  private Player determineWinner(Map<Player, Action> playerToAction) {
    ArrayList<Map.Entry<Player, Action>> entries = new ArrayList<>(playerToAction.entrySet());
    return determineWinner(
        entries.get(0).getKey(),
        entries.get(0).getValue(),
        entries.get(1).getKey(),
        entries.get(1).getValue());
  }

  private Player determineWinner(Player player1, Action action1, Player player2, Action action2) {
    Action winningAction = winningAction(action1, action2);
    if (winningAction == null) {
      return null;
    }
    return winningAction.equals(action1) ? player1 : player2;
  }
}
