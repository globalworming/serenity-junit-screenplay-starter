package com.example.screenplay.action;

import com.example.GameService;
import com.example.screenplay.ability.ManageGames;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RemovesPlayerCalled implements Performable {
  private final String playerName;
  private final String gameName;

  RemovesPlayerCalled(String playerName) {
    this.playerName = playerName;
    gameName = null;
  }

  RemovesPlayerCalled(String playerName, String gameName) {
    this.playerName = playerName;
    this.gameName = gameName;
  }

  @Override
  @Step("{0} deletes player #playerName from game")
  public <T extends Actor> void performAs(T actor) {
    GameService service = ManageGames.as(actor).getService();
    service.remove(gameName, playerName);
  }

  Performable from(String gameName) {
    return instrumented(RemovesPlayerCalled.class, playerName, gameName);
  }
}
