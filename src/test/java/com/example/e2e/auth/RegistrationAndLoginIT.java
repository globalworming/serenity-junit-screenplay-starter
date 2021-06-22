package com.example.e2e.auth;

import static org.hamcrest.CoreMatchers.not;

import com.example.screenplay.actor.ActorPropertiesFactory;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.actor.ReceiveEmails;
import com.mailosaur.MailosaurClient;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.util.EnvironmentVariables;
import org.hamcrest.text.IsBlankString;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class RegistrationAndLoginIT {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  EnvironmentVariables environmentVariables;

  Actor user;

  @Before
  public void setUp() {
    user = new Actor("normal user");
    user.can(BrowseTheWeb.with(aBrowser));
    String apiKey = environmentVariables.getProperty("mailosaur.api.key");
    Assume.assumeThat(apiKey, not(IsBlankString.blankOrNullString()));
    user.can(ReceiveEmails.with(new MailosaurClient("qWEcUNZEMsqWkyNV")));
    user.remember(Memory.USERNAME, ActorPropertiesFactory.getUniqueUsername());
    user.remember(Memory.PASSWORD, "asdasdasd");
    user.remember(Memory.EMAIL_ADDRESS, ActorPropertiesFactory.randomUniqueEmailAddress());
  }

  @Test
  public void registerSomeUser() {
    // user.attemptsTo(new RegisterAccount());
    // user.should(seeThat(new TheyAreLoggedIn()));
  }
}
