package com.example.service;

import com.example.error.UnauthorizedException;
import com.example.model.Game;
import com.example.model.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.model.Role.ADMIN;

public class GameService {
  private static final GameService instance = new GameService();
  private final AuthenticateService authenticateService = AuthenticateService.getInstance();

  private final Map<String, Game> gameNameToGame = new HashMap<>();

  static GameService getInstance() {
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

  public void remove(String accessToken, String gameName, String playerName) {
    checkPermissions(accessToken, ADMIN);
    getGame(gameName).getPlayers().removeIf(player -> playerName.equals(player.getName()));
  }

  private void checkPermissions(String accessToken, Role role) {
    List<Role> currentRoles = authenticateService.getCurrentRoles(accessToken);
    if (!currentRoles.contains(role)) {
      throw new UnauthorizedException();
    }
  }
}
