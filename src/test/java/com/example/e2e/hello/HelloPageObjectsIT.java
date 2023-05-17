package com.example.e2e.hello;

import com.example.screenplay.page.ExampleIssueTracker;
import com.example.screenplay.page.ExampleIssueTracker.ExampleIssueDetails;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class HelloPageObjectsIT {

  Actor tester = new OnlineCast().actorNamed("actor");

  @Test
  void navigate() {
    tester.attemptsTo(Open.browserOn(new ExampleIssueTracker()));
    tester.attemptsTo(Ensure.thatTheCurrentPage().currentUrl().isEqualTo("https://example.com/issues"));
  }

  @Test
  void oneWayToDoUrlParameters() {
    tester.attemptsTo(Open.browserOn().the(ExampleIssueDetails.class).withParameters("test"));
    tester.attemptsTo(Ensure.thatTheCurrentPage().currentUrl().isEqualTo("https://example.com/issues/test"));
  }
}
