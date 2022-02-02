package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

public class SelectSerenityCorePackage implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        Open.url(
            "https://github.com/serenity-bdd/serenity-core/network/dependents?package_id=UGFja2FnZS0xODE2OTAxNzM%3D"));
  }
}
