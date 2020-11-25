package com.example.e2e.browser;

import com.example.screenplay.action.AccessTheLatestReport;
import com.example.screenplay.action.LookUpAllUnsuccessfulOutcomes;
import com.example.screenplay.question.OutcomesShown;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.CountQuestion;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SerenityRunner.class)
@Narrative(text = "describes the report behavior")
public class ReportNavigatorIT {

  @Managed(driver = "chrome")
  private WebDriver aBrowser;

  @Test
  public void whenTracingErrors() {
    Actor tester = new Actor("tester");
    tester.can(BrowseTheWeb.with(aBrowser));
    tester.attemptsTo(new AccessTheLatestReport());
    tester.attemptsTo(new LookUpAllUnsuccessfulOutcomes());
    tester.should(eventually(seeThat(new CountQuestion(new OutcomesShown()), is(9))));
  }
}
