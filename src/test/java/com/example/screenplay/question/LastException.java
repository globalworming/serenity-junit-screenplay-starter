package com.example.screenplay.question;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

public class LastException extends QuestionWithDefaultSubject<Exception> {
  @Override
  public Exception answeredBy(Actor actor) {
    return actor.asksFor(actor.recall(Memory.EXCEPTION));
  }
}
