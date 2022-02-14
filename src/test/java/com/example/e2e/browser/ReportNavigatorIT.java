package com.example.e2e.browser;

import com.example.screenplay.action.AccessTheLatestReport;
import com.example.screenplay.action.LookUpAllUnsuccessfulOutcomes;
import com.example.screenplay.question.browser.OutcomesShown;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.CountQuestion;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
}
