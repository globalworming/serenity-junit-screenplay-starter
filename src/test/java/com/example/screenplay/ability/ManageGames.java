package com.example.screenplay.ability;

import com.example.GameService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class ManageGames implements Ability, RefersToActor {
  private final GameService gameService;
  private Actor actor;

  private ManageGames(GameService gameService) {
    this.gameService = gameService;
  }

  public static ManageGames through(GameService gameService) {
    return new ManageGames(gameService);
  }

  public static ManageGames as(Actor actor) {
    if (actor.abilityTo(ManageGames.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(ManageGames.class).asActor(actor);
  }


  @Override
  public ManageGames asActor(Actor actor) {
    this.actor = actor;
    return this;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  public GameService getService() {
    return gameService;
  }
}
