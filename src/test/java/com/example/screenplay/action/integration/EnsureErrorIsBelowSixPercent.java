package com.example.screenplay.action.integration;

import com.example.screenplay.question.integration.CurrentError;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.lessThan;

public class EnsureErrorIsBelowSixPercent implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.should(seeThat(new CurrentError(), lessThan(0.06)));
  }
}
