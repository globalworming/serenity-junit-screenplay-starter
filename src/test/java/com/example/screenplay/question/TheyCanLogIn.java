package com.example.screenplay.question;

import com.example.screenplay.action.LogIn;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;

public class TheyCanLogIn extends
    QuestionWithDefaultSubject<Boolean> {

  @Override
  public Boolean answeredBy(Actor actor) {
    actor.attemptsTo(new LogIn());
    return actor.asksFor(new AmILoggedIn());
  }
}
