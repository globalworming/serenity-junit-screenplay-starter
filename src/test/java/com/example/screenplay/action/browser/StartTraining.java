package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

public class StartTraining implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Click.on(Target.the("train a few rounds").locatedBy(".e2e-do-train")));
  }
}
