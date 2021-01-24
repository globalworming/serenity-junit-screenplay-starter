package com.example.screenplay.ability;

import com.example.GameAdminService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class ManageGames implements Ability, RefersToActor {
  private final GameAdminService gameAdminService;
  private Actor actor;

  private ManageGames(GameAdminService gameAdminService) {
    this.gameAdminService = gameAdminService;
  }

  public static ManageGames through(GameAdminService gameAdminService) {
    return new ManageGames(gameAdminService);
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

  public GameAdminService getService() {
    return gameAdminService;
  }
}
