package com.example.screenplay.question.browser;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

@RequiredArgsConstructor
public class TheConfidence implements Question<Double> {

  private final String color;

  public static TheConfidence of(String color) {
    return new TheConfidence(color);
  }

  @Override
  public Double answeredBy(Actor actor) {
    Target confidence =
        Target.the("confidence of color being " + color)
            .locatedBy(".e2e-inference-confidence-for-label-" + color);
    String text = confidence.resolveFor(actor).getText();
    return Double.valueOf(text);
  }
}
