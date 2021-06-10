package com.example.screenplay.action;

import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FillRegistrationForm implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(Enter.theValue("test3").into("[type=text]"));
    actor.attemptsTo(Enter.theValue("example@example.com").into("[type=email]"));
    actor.attemptsTo(Enter.theValue("test3").into("[type=password]"));
    actor.attemptsTo(Enter.theValue("test3").into("//div[4]/input"));

    List<WebElementFacade> inputs = Target.the("inputs").located(By.cssSelector("input"))
        .resolveAllFor(actor);
    inputs.get(4).click();
    inputs.get(5).click();
  }
}
