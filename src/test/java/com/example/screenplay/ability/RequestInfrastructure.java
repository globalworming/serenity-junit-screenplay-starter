package com.example.screenplay.ability;

import com.example.mock.InfrastructureService;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;

public class RequestInfrastructure implements RefersToActor, Ability {
  private final InfrastructureService service;
  private Actor actor;

  public RequestInfrastructure(InfrastructureService service) {
    this.service = service;
  }


  @Override
  public RequestInfrastructure asActor(Actor actor) {
    this.actor = actor;
    return this;
  }

  public static RequestInfrastructure as(Actor actor) {
    if (actor.abilityTo(RequestInfrastructure.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    } else {
      return actor.abilityTo(RequestInfrastructure.class).asActor(actor);
    }
  }

  public static RequestInfrastructure with(InfrastructureService service) {
    return new RequestInfrastructure(service);
  }

  public InfrastructureService getService() {
    return service;
  }
}
