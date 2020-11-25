package com.example.e2e.browser;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SerenityRunner.class)
public class BrowserIT {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void whenDoingAnExample() {
    Actor tester = new Actor("tester");
    tester.can(BrowseTheWeb.with(aBrowser));
    tester.attemptsTo(Open.url("https://example.com"));
    tester.should(seeThat(a -> BrowseTheWeb.as(a).getDriver().findElement(By.cssSelector("body")).getText(), containsString("Example Domain")));
  }

  @Test
  public void whenWeNeedAMission() {
    // accessing a test report:
    // navigate to report page https://lemon-desert-049177e03.azurestaticapps.net/
    // find the quicklinks by clicking the filter button
    // click "trace errors"
    // ensure 9 test results are shown
  }

}
