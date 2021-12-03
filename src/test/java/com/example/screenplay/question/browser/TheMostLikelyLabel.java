package com.example.screenplay.question.browser;

import com.example.screenplay.action.browser.SelectColor;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

@RequiredArgsConstructor
public class TheMostLikelyLabel implements Question<String> {

  private final String s;

  public static TheMostLikelyLabel of(String color) {
    String s = color;
    if (s.startsWith("#")) {
      s = s.substring(1);
    }
    return new TheMostLikelyLabel(s);
  }

  @Override
  public String answeredBy(Actor actor) {
    actor.attemptsTo(SelectColor.withValue(s));
    return Target.the("most likely label")
        .locatedBy(".e2e-show-confidence-label")
        .resolveFor(actor)
        .getText();
  }
}
