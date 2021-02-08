package com.example.screenplay.ability;

import com.example.PlayService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class PlayAGame implements Ability, RefersToActor {
  private final PlayService playService;
  private Actor actor;

  private PlayAGame(PlayService playService) {
    this.playService = playService;
  }

  public static PlayAGame through(PlayService playService) {
    return new PlayAGame(playService);
  }

  public static PlayAGame as(Actor actor) {
    if (actor.abilityTo(PlayAGame.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(PlayAGame.class).asActor(actor);
  }


  @Override
  public PlayAGame asActor(Actor actor) {
    this.actor = actor;
    return this;
  }

  @Override
  public String toString() {
    return "Observe the game";
  }

  public PlayService getService() {
    return playService;
  }
}
