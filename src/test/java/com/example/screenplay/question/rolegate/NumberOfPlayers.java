package com.example.screenplay.question.rolegate;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;

public class NumberOfPlayers extends QuestionWithDefaultSubject<Long> {
  public NumberOfPlayers(String gameId) {}

  @Override
  public Long answeredBy(Actor actor) {
    return null;
  }
}
