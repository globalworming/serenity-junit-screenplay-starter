package com.example.screenplay.action;

import com.example.screenplay.page.NavigatorDemo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

public class AccessTheLatestReport implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    t.attemptsTo(
        Open.browserOn(new NavigatorDemo())
    );
    t.should(seeThat(
        the(NavigatorDemo.shareCurrentView),
        isVisible()
    ));
  }
}
