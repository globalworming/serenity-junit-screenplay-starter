package com.example.screenplay.question.browser;

import com.example.screenplay.action.browser.SelectColor;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

@RequiredArgsConstructor
public class TheMostLikelyLabel implements Question<String> {

  private final String color;

  public static TheMostLikelyLabel of(String color) {
    return new TheMostLikelyLabel(color.substring(1));
  }

  @Override
  public String answeredBy(Actor actor) {
    actor.attemptsTo(Open.url("http://localhost:3000"));
    actor.attemptsTo(new SelectColor(color));
    return Target.the("most likely label")
        .locatedBy(".e2e-inference-results dt")
        .resolveFor(actor)
        .getText();
  }
}
