package com.example.screenplay.action;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class RemovesPlayer implements Performable {

  private final String name;

  private RemovesPlayer(String name) {
    this.name = name;
  }

  public static Performable called(String name) {
    return new RemovesPlayer(name);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    String gameName = actor.recall(Memory.GAME_NAME);
    actor.attemptsTo(new RemovesPlayerCalled(name).from(gameName));
  }
}
