package com.example.screenplay.question.rolegate;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class TheyAreLoggedIn extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    return actor.asksFor(new AmILoggedIn());
  }
}
