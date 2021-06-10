package com.example.screenplay.action;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Enter;

public class FillLoginForm implements Performable {

  @Override
  public <T extends Actor> void performAs(T actor) {
    String username = actor.recall(Memory.USERNAME);
    String password = actor.recall(Memory.PASSWORD);
    actor.attemptsTo(Enter.theValue(username).into("[type='text']"));
    actor.attemptsTo(Enter.theValue(password).into("[type=password]"));
  }
}
