package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

public class LookUpAllUnsuccessfulOutcomes implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    t.attemptsTo(Click.on(".ToggleSideMenu"));
    t.attemptsTo(Click.on(".traceErrors"));

  }
}
