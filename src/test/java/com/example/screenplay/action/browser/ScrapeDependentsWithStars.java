package com.example.screenplay.action.browser;

import com.example.screenplay.question.browser.DoesAnyHaveAStar;
import com.example.screenplay.question.browser.HasNextPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class ScrapeDependentsWithStars implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    boolean hasNextPage = actor.asksFor(new HasNextPage());
    do {
      Boolean listHasStars = actor.asksFor(new DoesAnyHaveAStar());
      if (listHasStars) {
        actor.attemptsTo(new PushReposAndStarsToBigquery());
        actor.attemptsTo(new GoToNextPage());
      } else if (hasNextPage) {
        actor.attemptsTo(new GoToNextPage());
      }
      hasNextPage = actor.asksFor(new HasNextPage());
    } while (hasNextPage);
  }
}
