package com.example.screenplay.action.preparation;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class ExtractDataToCorrectResourcesPath implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    Serenity.reportThat("extract to '.src/main/resources/xkcd'", () -> {});
  }
}
