package com.example.screenplay.action.hello;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GreetWorld implements Performable {

  private static Log log = LogFactory.getLog(GreetWorld.class);

  @Override
  public <T extends Actor> void performAs(T t) {
    log.info(t.recall("greeting"));
  }
}
