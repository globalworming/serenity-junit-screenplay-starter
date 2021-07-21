package com.example.e2e.browser

import com.example.screenplay.action.AccessTheLatestReport
import com.example.screenplay.action.LookUpAllUnsuccessfulOutcomes
import com.example.screenplay.question.OutcomesShown
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.EventualConsequence.*
import net.serenitybdd.screenplay.GivenWhenThen.*
import net.serenitybdd.screenplay.abilities.BrowseTheWeb
import net.serenitybdd.screenplay.questions.CountQuestion
import net.thucydides.core.annotations.Managed
import net.thucydides.core.annotations.Narrative
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver

@RunWith(SerenityRunner::class)
@Narrative(text = ["describes the report behavior"])
class ReportNavigatorIT {

  @Managed(driver = "chrome")
  private lateinit var aBrowser: WebDriver

  @Test
  fun whenTracingErrors() {
    val tester = Actor("tester")
    tester.can(BrowseTheWeb.with(aBrowser))
    tester.attemptsTo(AccessTheLatestReport())
    tester.attemptsTo(LookUpAllUnsuccessfulOutcomes())
    tester.should(eventually(seeThat(CountQuestion(OutcomesShown()), `is`(9))))
  }
}