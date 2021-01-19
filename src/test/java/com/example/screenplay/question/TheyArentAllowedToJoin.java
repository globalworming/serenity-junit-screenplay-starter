package com.example.screenplay.question;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TheyArentAllowedToJoin extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    String message = actor.recall(Memory.EXCEPTION);
    assertThat(message, is("only two players allowed"));
    Serenity.recordReportData().withTitle("Exception message").andContents(message);
    return true;
  }
}
