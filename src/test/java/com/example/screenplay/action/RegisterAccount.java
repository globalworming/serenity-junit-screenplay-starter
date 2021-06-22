package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

public class RegisterAccount implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new OpenHomePage());
    actor.attemptsTo(Click.on("//div[contains(text(), 'CREATE ACCOUNT')]"));
    actor.attemptsTo(new FillRegistrationForm());
    // actor.attemptsTo(new SubmitRegistrationForm());
    // actor.attemptsTo(new ConfirmEmailAddress());
  }
}
