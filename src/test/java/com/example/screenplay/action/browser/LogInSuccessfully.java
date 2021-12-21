package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.questions.WebElementQuestion;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class LogInSuccessfully implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(new LogIn());
    actor.should(
        seeThat(
            WebElementQuestion.the(
                Target.the("player name top left").locatedBy("//div[contains(text(), 'test4')]")),
            WebElementStateMatchers.isPresent()));
  }
}
