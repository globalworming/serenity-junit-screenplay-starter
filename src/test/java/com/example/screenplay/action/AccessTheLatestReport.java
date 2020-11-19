package com.example.screenplay.action;

import com.example.screenplay.NavigatorDemo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Open;

public class AccessTheLatestReport implements Performable {
  @Override
  public <T extends Actor> void performAs(T t) {
    t.attemptsTo(
        Open.browserOn(new NavigatorDemo())
    );
  }
}
