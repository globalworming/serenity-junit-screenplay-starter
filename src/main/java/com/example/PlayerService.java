package com.example;

public class PlayerService {

  private final LobbyService lobbyService = LobbyService.getInstance();

  public void joinGame(String player, String gameName) {
    lobbyService.joinGame(gameName, player);
  }

  public void createGame(String gameName) {
    lobbyService.createGame(gameName);
  }
}
