package com.example.screenplay.ability;

import com.example.GameAdminService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class ObserveTheGame implements Ability, RefersToActor {
  private final GameAdminService gameAdminService;
  private Actor actor;

  private ObserveTheGame(GameAdminService gameAdminService) {
    this.gameAdminService = gameAdminService;
  }

  public static ObserveTheGame through(GameAdminService gameAdminService) {
    return new ObserveTheGame(gameAdminService);
  }

  public static ObserveTheGame as(Actor actor) {
    if (actor.abilityTo(ObserveTheGame.class) == null) throw new NoMatchingAbilityException(actor.getName());
    return actor.abilityTo(ObserveTheGame.class).asActor(actor);
  }


  @Override
  public ObserveTheGame asActor(Actor actor) {
    this.actor = actor;
    return this;
  }

  @Override
  public String toString() {
    return "Observe the game";
  }

  public GameAdminService getService() {
    return gameAdminService;
  }
}
