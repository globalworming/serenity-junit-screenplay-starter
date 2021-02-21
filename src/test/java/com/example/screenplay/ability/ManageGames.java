package com.example.screenplay.ability;

import com.example.service.GameService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;

public class ManageGames implements Ability {
  private final GameService gameService;

  private ManageGames(GameService gameService) {
    this.gameService = gameService;
  }

  public static ManageGames through(GameService gameService) {
    return new ManageGames(gameService);
  }

  public static GameService as(Actor actor) {
    if (actor.abilityTo(ManageGames.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(ManageGames.class).getService();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private GameService getService() {
    return gameService;
  }
}
