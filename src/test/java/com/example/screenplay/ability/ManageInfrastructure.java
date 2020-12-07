package com.example.screenplay.ability;

import com.example.mock.OpsManagement;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.NoMatchingAbilityException;
import net.serenitybdd.screenplay.RefersToActor;


public class ManageInfrastructure implements Ability, RefersToActor {

  private Actor actor;
  private OpsManagement service;

  public ManageInfrastructure(OpsManagement service) {
    this.service = service;
  }

  public static ManageInfrastructure with(OpsManagement opsManagement) {
    return new ManageInfrastructure(opsManagement);
  }

  public ManageInfrastructure asActor(Actor actor) {
    this.actor = actor;
    return this;
  }

  public static ManageInfrastructure as(Actor actor) {
    if (actor.abilityTo(ManageInfrastructure.class) == null) {
      throw new NoMatchingAbilityException(actor.getName());
    } else {
      return actor.abilityTo(ManageInfrastructure.class).asActor(actor);
    }
  }

  public OpsManagement getService() {
    return service;
  }
}
