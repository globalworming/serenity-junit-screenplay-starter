package com.example.e2e.browser;

import com.example.screenplay.NavigatorDemo;
import com.example.screenplay.action.AccessTheLatestReport;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

@RunWith(SerenityRunner.class)
@Narrative(text = "This describes our behavior regarding the browser")
public class ReportNavigatorIT {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void whenTracingErrors() {
    Actor tester = new Actor("tester");
    tester.can(BrowseTheWeb.with(aBrowser));
    tester.attemptsTo(new AccessTheLatestReport());
    tester.should(seeThat(
        the(NavigatorDemo.shareCurrentView),
        isVisible()
    ));
  }
}
