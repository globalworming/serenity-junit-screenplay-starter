package com.example.screenplay.question.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public class NumberOfFacts extends QuestionWithDefaultSubject<Integer> {
  @Override
  public Integer answeredBy(Actor actor) {
    return Target.the("facts").locatedBy(".e2e-show-fact").resolveAllFor(actor).size();
  }
}
