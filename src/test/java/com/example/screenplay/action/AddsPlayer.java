package com.example.screenplay.action;

import com.example.GameService;
import com.example.screenplay.ability.ManageGames;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddsPlayer implements Performable {

  private final String name;
  private final String gameName;

  public AddsPlayer(String name) {
    this.name = name;
    gameName = null;
  }

  public AddsPlayer(String name, String gameName) {
    this.name = name;
    this.gameName = gameName;
  }

  static AddsPlayer withId(String name) {
    return instrumented(AddsPlayer.class, name);
  }

  @Override
  @Step("{0} adds player #name to game")
  public <T extends Actor> void performAs(T actor) {
    GameService service = ManageGames.as(actor).getService();
    service.addPlayer(name, gameName);
  }

  AddsPlayer to(String gameName) {
    return instrumented(AddsPlayer.class, name, gameName);
  }
}
