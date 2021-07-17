package com.example.screenplay.question;

import net.serenitybdd.screenplay.Actor;

public class NumberOfPlayers extends QuestionWithDefaultSubject<Long> {
  public NumberOfPlayers(String gameId) {
  }

  @Override
  public Long answeredBy(Actor actor) {
    return null;
  }
}
