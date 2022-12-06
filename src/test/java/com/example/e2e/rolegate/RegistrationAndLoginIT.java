package com.example.e2e.rolegate;

import com.example.screenplay.action.RegisterAccount;
import com.example.screenplay.actor.ActorPropertiesFactory;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.actor.ReceiveEmails;
import com.example.screenplay.question.rolegate.TheyAreLoggedIn;
import com.mailosaur.MailosaurClient;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.environment.SystemEnvironmentVariables;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class RegistrationAndLoginIT {

  EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
  Actor user;

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  @Disabled(
      "this process doesnt work anymore. it may still serve as example how to use Mailosaur")
  public void registerSomeUser() {
    user = new Actor("normal user");
    user.can(BrowseTheWeb.with(aBrowser));
    String apiKey = environmentVariables.getProperty("MAILOSAUR_API_KEY");
    Assumptions.assumeTrue(apiKey != null);
    user.can(ReceiveEmails.with(new MailosaurClient(apiKey)));
    user.remember(Memory.USERNAME, ActorPropertiesFactory.getUniqueUsername());
    user.remember(Memory.PASSWORD, "asdasdasd");
    user.remember(Memory.EMAIL_ADDRESS, ActorPropertiesFactory.randomUniqueEmailAddress());
    user.attemptsTo(new RegisterAccount());
    user.should(seeThat(new TheyAreLoggedIn()));
  }
}
