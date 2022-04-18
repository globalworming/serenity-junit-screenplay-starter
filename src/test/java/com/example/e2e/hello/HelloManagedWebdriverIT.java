package com.example.e2e.hello;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.OpenUrl;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(SerenityJUnit5Extension.class)
@Narrative(text = {"try managed webdriver"})
public class HelloManagedWebdriverIT {

  @Managed WebDriver driver;

  @Test
  public void weCanMakeAssertionAboutPageTitle() {
    Actor warren = Actor.named("Warren").whoCan(BrowseTheWeb.with(driver));

    warren.attemptsTo(
        new OpenUrl("http://todomvc.com/%22)"),
        Ensure.thatTheCurrentPage().title().isEqualTo("Page not found · GitHub Pages"));
  }
}
