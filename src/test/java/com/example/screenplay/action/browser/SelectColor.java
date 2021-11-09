package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class SelectColor implements Performable {
  private final String s;

  public SelectColor(String color) {
    if (color.startsWith("#")) {
      s = color.substring(1);
    } else {
      s = color;
    }
  }

  public static Performable withValue(String color) {
    return instrumented(SelectColor.class, color);
  }

  @Override
  @Step("{0} select color 0x#s")
  public void performAs(Actor actor) {
    Target setColor = Target.the("set color " + s + " button").locatedBy(".e2e-do-set-color-" + s);
    actor.attemptsTo(WaitUntil.the(setColor, isEnabled()));
    setColor.resolveFor(actor).click();
  }
}
