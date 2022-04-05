package com.example.e2e.browser;

import com.example.screenplay.action.AccessTheLatestReport;
import com.example.screenplay.action.LookUpAllUnsuccessfulOutcomes;
import com.example.screenplay.action.browser.CaptureDurationDistributionAsEvidence;
import com.example.screenplay.action.browser.EnsureButtonTitles;
import com.example.screenplay.question.browser.OutcomesShown;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.CountQuestion;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v99.network.Network;

import java.util.Optional;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@ExtendWith(SerenityJUnit5Extension.class)
@Narrative(text = "describes the report behavior")
public class ReportNavigatorIT {

  private OnlineCast cast = new OnlineCast();

  @Test
  public void whenTracingErrors() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new LookUpAllUnsuccessfulOutcomes());
    tester.should(eventually(seeThat(new CountQuestion(new OutcomesShown()), is(9))));
  }

  @Test
  public void selenium4screenshotExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new CaptureDurationDistributionAsEvidence());
  }

  @Test
  public void selenium4relativeLocatorsExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new EnsureButtonTitles());
  }

  @Test
  public void selenium4devToolsExample() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    DevTools devTools = BrowseTheWeb.as(tester).getDevTools();
    devTools.createSessionIfThereIsNotOne();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
    devTools.addListener(
        Network.responseReceived(),
        entry -> {
          if (entry.getResponse().getStatus() != 200) {
            throw new RuntimeException("TODO");
          }
          System.out.println(
              "Response (Req id) URL : ("
                  + entry.getRequestId()
                  + ") "
                  + entry.getResponse().getUrl()
                  + " ("
                  + entry.getResponse().getStatus()
                  + ")");
        });
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(Open.url("https://example.com/"));
  }
}
