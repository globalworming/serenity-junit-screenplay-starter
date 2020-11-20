package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetAListOfAllPosts implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    t.attemptsTo(Get.resource("/posts"));
  }
}
