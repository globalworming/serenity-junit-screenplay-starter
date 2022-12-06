package com.example.screenplay.action.browser;

import com.example.screenplay.page.GithubDependendsPage;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.time.Duration;

public class GoToNextPage implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    val currentUrl = BrowseTheWeb.as(actor).getDriver().getCurrentUrl();
    actor.attemptsTo(Click.on(GithubDependendsPage.NEXT_PAGE_BUTTON));
    ExpectedCondition<Boolean> urlChanges = webDriver -> webDriver != null && !webDriver.getCurrentUrl().equals(currentUrl);
    actor.attemptsTo(WaitUntil.the(urlChanges).forNoMoreThan(Duration.ofMillis(30000)));
  }
}
