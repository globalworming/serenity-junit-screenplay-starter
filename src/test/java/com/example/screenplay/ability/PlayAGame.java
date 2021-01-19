package com.example.screenplay.ability;

import com.example.PlayerService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class PlayAGame implements Ability, RefersToActor {
  private final PlayerService playerService;
  private Actor actor;

  private PlayAGame(PlayerService playerService) {
    this.playerService = playerService;
  }

  public static PlayAGame through(PlayerService playerService) {
    return new PlayAGame(playerService);
  }

  public static PlayAGame as(Actor actor) {
    if (actor.abilityTo(PlayAGame.class) == null) throw new NoMatchingAbilityException(actor.getName());
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

  public PlayerService getService() {
    return playerService;
  }
}
