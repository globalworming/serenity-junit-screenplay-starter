package com.example.screenplay.action;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import java.util.List;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class SetupGame implements Performable {
  private final List<Actor> players;

  SetupGame(List<Actor> players) {
    this.players = players;
  }

  public static Performable forPlayers(List<Actor> actors) {
    return instrumented(SetupGame.class, actors);
  }

  @Override
  @Step("{0} sets up game for #players")
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new CreatesGame());
    String gameName = actor.recall(Memory.GAME_NAME);
    players.forEach(player -> actor.attemptsTo(AddsPlayer.withId(player.getName()).to(gameName)));
  }
}
