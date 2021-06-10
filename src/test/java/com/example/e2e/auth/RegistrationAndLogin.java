package com.example.e2e.auth;

import com.example.screenplay.action.RegisterAccount;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.TheyCanLogIn;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@RunWith(SerenityRunner.class)
public class RegistrationAndLogin {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void registerSomeUser() {
    Actor tester = new Actor("tester");
    tester.can(BrowseTheWeb.with(aBrowser));
    tester.remember(Memory.USERNAME, "test2");
    tester.remember(Memory.PASSWORD, "asdasdasd");
    tester.attemptsTo(new RegisterAccount());
    tester.should(seeThat(new TheyCanLogIn()));
  }
}
