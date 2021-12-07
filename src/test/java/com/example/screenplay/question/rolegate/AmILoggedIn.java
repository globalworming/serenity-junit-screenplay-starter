package com.example.screenplay.question.rolegate;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Presence;

public class AmILoggedIn extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    return actor.asksFor(Presence.of("//a/div/div[contains(text(), '\uF343')]"));
  }
}
