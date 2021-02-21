package com.example.screenplay.question;

import com.example.model.Role;
import com.example.screenplay.ability.GetAuthorized;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

public class TheirRoles extends QuestionWithDefaultSubject<List<Role>> {
  @Override
  public List<Role> answeredBy(Actor actor) {
    String accessToken = actor.recall(Memory.ACCESS_TOKEN);
    return GetAuthorized.as(actor).getCurrentRoles(accessToken);
  }
}
