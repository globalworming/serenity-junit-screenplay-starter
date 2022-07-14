package com.example.screenplay.action.browser;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class TakeScreenshot implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    Serenity.takeScreenshot();
  }
}
