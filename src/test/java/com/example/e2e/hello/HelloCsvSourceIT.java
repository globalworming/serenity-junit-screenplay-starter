package com.example.e2e.hello;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@ExtendWith(SerenityJUnit5Extension.class)
public class HelloCsvSourceIT {

  @ParameterizedTest
  @CsvSource({
      // actor, properties,
      "'juan', 'age=3;future=bright'",
      "'marlene','age=17;machine=80%'"
  })
  void whereActorsReportInParameterizedFashion(String actorName, String properties) {
    Serenity.reportThat(actorName + " reports: ", () -> {
      Actor actor = new Actor(actorName);
      for (String property : properties.split(";")) {
        actor.attemptsTo(Task.where("{0} reports " + property, (a -> {
          System.out.println(a.getName() + " has " + property.split("=")[0] + " of " + property.split("=")[1]);
        })));
      }
    });
  }

}
