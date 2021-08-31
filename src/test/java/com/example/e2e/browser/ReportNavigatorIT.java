package com.example.e2e.browser;

import com.example.screenplay.action.AccessTheLatestReport;
import com.example.screenplay.action.LookUpAllUnsuccessfulOutcomes;
import com.example.screenplay.question.OutcomesShown;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.questions.CountQuestion;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.Test;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@Narrative(text = "describes the report behavior")
class ReportNavigatorIT {

  private OnlineCast cast = new OnlineCast();

  @Test
  void whenTracingErrors() {
    Actor tester = cast.actorNamed("tester");
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new LookUpAllUnsuccessfulOutcomes());
    tester.should(eventually(seeThat(new CountQuestion(new OutcomesShown()), is(9))));
  }
}
