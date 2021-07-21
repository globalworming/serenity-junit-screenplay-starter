package com.example.e2e.browser

import com.example.screenplay.question.image.NoDifferenceToSnapshot
import net.serenitybdd.junit.runners.SerenityRunner
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.GivenWhenThen.*
import net.serenitybdd.screenplay.abilities.BrowseTheWeb
import net.serenitybdd.screenplay.actions.Open
import net.thucydides.core.annotations.Managed
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver

@RunWith(SerenityRunner::class)
class CompareScreenshotsIT {

  @Managed(driver = "chrome")
  private lateinit var aBrowser: WebDriver

  @Test
  fun `when comparing full size page`() {
    val tester = Actor("tester")
    tester.can(BrowseTheWeb.with(aBrowser))
    tester.attemptsTo(Open.url("https://www.gns.cri.nz/Home/Our-Science/Energy-Futures/Oil-and-Gas/Petroleum-Basin-Explorer"))
    tester.should(seeThat(NoDifferenceToSnapshot("homepage.png"), `is`(true)))
  }
}