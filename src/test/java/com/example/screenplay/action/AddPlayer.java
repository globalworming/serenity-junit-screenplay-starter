package com.example.screenplay.action;

import com.example.GameService;
import com.example.screenplay.ability.ManageGames;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AddPlayer implements Performable {

  private final String name;
  private final String gameName;

  public AddPlayer(String name) {
    this.name = name;
    gameName = null;
  }

  public AddPlayer(String name, String gameName) {
    this.name = name;
    this.gameName = gameName;
  }

  static AddPlayer withId(String name) {
    return instrumented(AddPlayer.class, name);
  }

  @Override
  @Step("{0} adds player #name to game #gameName")
  public <T extends Actor> void performAs(T actor) {
    GameService service = ManageGames.as(actor).getService();
    service.addPlayer(name, gameName);
  }

  AddPlayer to(String gameName) {
    return instrumented(AddPlayer.class, name, gameName);
  }
}
