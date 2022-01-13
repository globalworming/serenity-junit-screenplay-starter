package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

public class SubmitRegistrationForm implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Click.on("//a[2]/div/div[contains(text(), 'CREATE')]"));
  }
}
