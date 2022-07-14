package com.example.e2e.hello;

import com.example.screenplay.action.browser.TakeScreenshot;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class HelloScreenshotIT {

  Actor tester = new OnlineCast().actorUsingBrowser("chrome").named("actor");

  @Test
  void takeScreenshot() {
    tester.attemptsTo(Open.url("https://example.com"));
    tester.attemptsTo(new TakeScreenshot());
  }
}
