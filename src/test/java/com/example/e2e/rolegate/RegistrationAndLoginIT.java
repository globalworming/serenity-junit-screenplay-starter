package com.example.e2e.rolegate;

import com.example.screenplay.action.RegisterAccount;
import com.example.screenplay.actor.ActorPropertiesFactory;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.actor.ReceiveEmails;
import com.example.screenplay.question.rolegate.TheyAreLoggedIn;
import com.mailosaur.MailosaurClient;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@RunWith(SerenityRunner.class)
public class RegistrationAndLoginIT {

  EnvironmentVariables environmentVariables;
  Actor user;

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void registerSomeUser() {
    user = new Actor("normal user");
    user.can(BrowseTheWeb.with(aBrowser));
    String apiKey = environmentVariables.getProperty("MAILOSAUR_API_KEY");
    Assume.assumeNotNull(apiKey);
    user.can(ReceiveEmails.with(new MailosaurClient(apiKey)));
    user.remember(Memory.USERNAME, ActorPropertiesFactory.getUniqueUsername());
    user.remember(Memory.PASSWORD, "asdasdasd");
    user.remember(Memory.EMAIL_ADDRESS, ActorPropertiesFactory.randomUniqueEmailAddress());
    user.attemptsTo(new RegisterAccount());
    user.should(seeThat(new TheyAreLoggedIn()));
  }
}
