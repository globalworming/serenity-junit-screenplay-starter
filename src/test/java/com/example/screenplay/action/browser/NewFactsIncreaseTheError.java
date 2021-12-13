package com.example.screenplay.action.browser;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.val;
import net.serenitybdd.screenplay.Actor;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class NewFactsIncreaseTheError extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    val errorBefore = actor.asksFor(new CurrentError());
    actor.attemptsTo(new AddFact());
    actor.should(seeThat(new CurrentError(), greaterThan(errorBefore)));
    return true;
  }
}
