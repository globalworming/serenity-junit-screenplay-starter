package com.example.screenplay.action;

import com.example.screenplay.actor.Memory;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class FillRegistrationForm implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    String username = actor.recall(Memory.USERNAME);
    actor.attemptsTo(Enter.theValue(username).into("[type=text]"));
    String emailAddress = actor.recall(Memory.EMAIL_ADDRESS);
    actor.attemptsTo(Enter.theValue(emailAddress).into("[type=email]"));
    actor.attemptsTo(Enter.theValue("test3").into("[type=password]"));
    actor.attemptsTo(Enter.theValue("test3").into("//div[4]/input"));

    List<WebElementFacade> inputs =
        Target.the("inputs").located(By.cssSelector("input")).resolveAllFor(actor);
    inputs.get(4).click();
    inputs.get(5).click();
  }
}
