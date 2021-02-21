package com.example.screenplay.ability;

import com.example.service.PlayService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;

public class PlayAGame implements Ability {
  private final PlayService playService;

  private PlayAGame(PlayService playService) {
    this.playService = playService;
  }

  public static PlayAGame through(PlayService playService) {
    return new PlayAGame(playService);
  }

  public static PlayService as(Actor actor) {
    if (actor.abilityTo(PlayAGame.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(PlayAGame.class).getService();
  }

  @Override
  public String toString() {
    return "Play the game";
  }

  private PlayService getService() {
    return playService;
  }
}
