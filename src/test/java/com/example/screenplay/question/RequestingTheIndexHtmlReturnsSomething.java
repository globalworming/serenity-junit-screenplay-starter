package com.example.screenplay.question;


import net.serenitybdd.screenplay.Actor;

public class RequestingTheIndexHtmlReturnsSomething extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    return true;
  }
}
