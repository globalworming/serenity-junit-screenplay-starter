package com.example.screenplay.question;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;

import static org.junit.Assert.assertThrows;

public class TheyFailWhen extends QuestionWithDefaultSubject<Boolean> {
  private final Performable performable;

  private TheyFailWhen(Performable performable) {

    this.performable = performable;
  }

  public static Question<Boolean> perform(Performable performable) {
    return new TheyFailWhen(performable);
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    assertThrows(Exception.class, () -> actor.attemptsTo(performable));
    return true;
  }
}
