package com.example.screenplay.action.browser;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

@RequiredArgsConstructor
public class SelectColor implements Performable {

  final String colorValue;

  public static Performable withValue(String s) {
    if (s.startsWith("#")) {
      s = s.substring(1);
    }
    return new SelectColor(s);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Click.on(".e2e-do-set-color-" + colorValue));
  }
}
