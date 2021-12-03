package com.example.screenplay.question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Presence;

public class AmILoggedIn extends QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    return actor.asksFor(Presence.of("//a/div/div[contains(text(), '\uF343')]").asABoolean());
  }
}
