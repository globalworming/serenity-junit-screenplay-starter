package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;

public class JoinGame implements Performable {
  private final String gameSlug;

  public JoinGame(String gameSlug) {
    this.gameSlug = gameSlug;
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new LogInSuccessfully());
    //actor.attemptsTo(Check.whether(overlayExisst).andIfSo(CloseOverlay));
    actor.attemptsTo(Open.url("https://www.rolegate.com/" + gameSlug));
    actor.attemptsTo(
        Click.on(
            "//div[contains(text(), 'Join game')]"));
  }

}
