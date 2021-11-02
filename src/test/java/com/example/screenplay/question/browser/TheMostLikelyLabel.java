package com.example.screenplay.question.browser;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

@RequiredArgsConstructor
public class TheMostLikelyLabel implements Question<String> {

  private final String s;

  public static TheMostLikelyLabel of(String s) {
    return new TheMostLikelyLabel(s);
  }

  @Override
  public String answeredBy(Actor actor) {
    actor.attemptsTo(Open.url("http://localhost:3030"));
    actor.attemptsTo(Click.on("button"));
    Target.the("most likely label").locatedBy("something");
    return null;
  }
}
