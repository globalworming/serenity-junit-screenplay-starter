package com.example.screenplay.action

import com.example.screenplay.page.NavigatorDemo
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.GivenWhenThen.*
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.actions.Open
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*
import net.serenitybdd.screenplay.questions.WebElementQuestion.*

open class AccessTheLatestReport : Performable {
  override fun <T : Actor> performAs(actor: T) {
    actor.attemptsTo(Open.browserOn(NavigatorDemo()))
    actor.should(seeThat(the(NavigatorDemo.shareCurrentView), isVisible()))
  }
}