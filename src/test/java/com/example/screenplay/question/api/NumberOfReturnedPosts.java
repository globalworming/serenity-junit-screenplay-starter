package com.example.screenplay.question.api;

import com.example.screenplay.question.QuestionWithDefaultSubject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class NumberOfReturnedPosts extends QuestionWithDefaultSubject<Integer> {

  @Override
  public Integer answeredBy(Actor actor) {
    return actor.asksFor(new LastResponse()).jsonPath().getList("").size();
  }
}
