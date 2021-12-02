package com.example.screenplay.question.browser;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

@RequiredArgsConstructor
public class TheConfidence implements Question<Double> {

  private final String color;

  public static TheConfidence of(String s) {
    return new TheConfidence(s);
  }

  @Override
  public Double answeredBy(Actor actor) {
    String confidence =
        Target.the("confidence for label " + color)
            .locatedBy(".e2e-inference-confidence-for-label-" + color)
            .resolveFor(actor)
            .getText();
    Serenity.recordReportData()
        .withTitle(color + " confidence")
        .andContents(String.valueOf(confidence));
    return Double.parseDouble(confidence);
  }
}
