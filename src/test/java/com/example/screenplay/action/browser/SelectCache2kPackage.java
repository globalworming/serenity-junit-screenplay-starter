package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

public class SelectCache2kPackage implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        Open.url(
            "https://github.com/cache2k/cache2k/network/dependents?package_id=UGFja2FnZS0xODEyNDM4MTk%3D"));
  }
}
