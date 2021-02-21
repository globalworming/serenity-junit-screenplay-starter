package com.example.screenplay.question;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Remembered;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

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
    actor.attemptsTo(performable);
    actor.should(seeThat(Remembered.valueOf(Memory.EXCEPTION), not(nullValue())));
    return true;
  }
}
