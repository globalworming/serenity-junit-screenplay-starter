package com.example.e2e.hello;

import com.example.screenplay.action.hello.GreetWorld;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SerenityJUnit5Extension.class)
@Narrative(text = {"minimal test"})
public class HelloWorldIT {

  public static final String HELLO_WORLD = "Hello World";
  Actor greeter = new Actor("Greta");

  @BeforeEach
  void setUp() {
    greeter.remember("greeting", HELLO_WORLD);
  }

  @Test
  void serenityInitializes() {
    Serenity.reportThat(
        "check different serenity features",
        () -> {
          greeter.attemptsTo(new GreetWorld());
          Serenity.recordReportData().asEvidence().withTitle("greeting").andContents(HELLO_WORLD);
          fail();
        });
    Serenity.reportThat("all good", () -> {});
  }
}
