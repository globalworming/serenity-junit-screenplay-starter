package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;

public class SelectColor implements Performable {
  private final String s;

  public SelectColor(String color) {
    s = color;
  }

  @Override
  public void performAs(Actor actor) {
    Target.the("set color " + s + " button")
        .locatedBy(".e2e-do-set-color-" + s)
        .resolveFor(actor)
        .click();
  }
}
