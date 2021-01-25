package com.example;

public class PlayerService {

  private final LobbyService lobbyService = LobbyService.getInstance();

  public void joinGame(String player, String gameName) {
    lobbyService.joinGame(gameName, player);
  }

  public void createGame(String gameName) {
    lobbyService.createGame(gameName);
  }

  public String getWinner(String gameName) {
    return lobbyService.getGame(gameName).getWinner().getName();
  }

  public void pickAction(String gameName, String player, Action action) {
    lobbyService.getGame(gameName).pickAction(player, action);
  }
}
