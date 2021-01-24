package com.example;

public class GameAdminService {

  private LobbyService lobbyService = LobbyService.getInstance();

  public Game getGame(String name) {
    return lobbyService.getGame(name);
  }

  public void createGame(Game game) {
    lobbyService.createGame(game);
  }
}
