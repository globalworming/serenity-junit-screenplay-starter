package com.example.screenplay.ability;

import com.example.service.AuthenticateService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;

public class GetAuthorized implements Ability {

  private final AuthenticateService service;

  private GetAuthorized(AuthenticateService service) {
    this.service = service;
  }

  public static <T extends Actor> AuthenticateService as(T actor) {
    if (actor.abilityTo(GetAuthorized.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    }
    return actor.abilityTo(GetAuthorized.class).getService();
  }

  public static GetAuthorized through(AuthenticateService authenticateService) {
    return new GetAuthorized(authenticateService);
  }

  private AuthenticateService getService() {
    return service;
  }
}
