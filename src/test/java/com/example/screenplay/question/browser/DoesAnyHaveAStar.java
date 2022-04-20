package com.example.screenplay.question.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public class DoesAnyHaveAStar extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    return Target.the("stars")
        .locatedBy("[data-test-id=\"dg-repo-pkg-dependent\"] div span").resolveAllFor(actor)
        .stream()
        .anyMatch(it -> !it.getText().contains("0"));
  }
}
