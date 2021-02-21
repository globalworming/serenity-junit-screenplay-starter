package com.example.screenplay.action;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class Authenticate implements Performable {

  private final String credentials;

  public Authenticate(String credentials) {
    this.credentials = credentials;
  }

  public static Performable with(String credentials) {
    return instrumented(Authenticate.class, credentials);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    // GetAuthorized.as(actor).authenticate(actor.getName(), credentials);
  }
}
