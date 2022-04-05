package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;

public class EnsureButtonTitles implements Performable {

  Target firstBookmark = Target.the("first bookmark").locatedBy(".traceErrors");
  Target secondBookmark =
      Target.the("second bookmark")
          .located(
              RelativeLocator.with(By.cssSelector("button")).below(By.cssSelector(".traceErrors")));

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Click.on(".ToggleSideMenu"));
    Assertions.assertEquals("TRACE ERRORS", firstBookmark.resolveFor(actor).getText());
    Assertions.assertEquals("PENDING STORIES", secondBookmark.resolveFor(actor).getText());
  }
}
