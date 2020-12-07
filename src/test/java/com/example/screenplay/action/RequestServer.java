package com.example.screenplay.action;

import com.example.screenplay.ability.RequestInfrastructure;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

public class RequestServer implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    RequestInfrastructure.as(t).getService().requestNewServer(t.getName());
  }
}
