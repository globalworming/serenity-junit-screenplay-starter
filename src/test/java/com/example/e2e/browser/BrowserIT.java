package com.example.e2e.browser;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class BrowserIT {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void whenDoingAnExample() {
    Actor tester = new Actor("tester");
    tester.can(BrowseTheWeb.with(aBrowser));
  }
}
