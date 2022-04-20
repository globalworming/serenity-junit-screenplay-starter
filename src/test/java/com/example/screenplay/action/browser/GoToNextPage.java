package com.example.screenplay.action.browser;

import com.example.screenplay.page.GithubDependendsPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;

public class GoToNextPage implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Click.on(GithubDependendsPage.NEXT_PAGE_BUTTON));
  }
}
