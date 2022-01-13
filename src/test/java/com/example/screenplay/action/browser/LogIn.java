package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

public class LogIn implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new OpenHomePage());
    Target login = Target.the("login button on the homepage").locatedBy(".call_to_action");
    actor.attemptsTo(Click.on(login));
    actor.attemptsTo(new FillLoginForm());
    actor.attemptsTo(Click.on("//a[2]/div/div[contains(text(), 'LOGIN')]"));
  }
}
