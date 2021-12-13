package com.example.screenplay.action.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Text;

public class CurrentError extends QuestionWithDefaultSubject<Double> {
  @Override
  public Double answeredBy(Actor actor) {
    return actor.asksFor(Text.of(".e2e-show-current-error").asDouble());
  }
}
