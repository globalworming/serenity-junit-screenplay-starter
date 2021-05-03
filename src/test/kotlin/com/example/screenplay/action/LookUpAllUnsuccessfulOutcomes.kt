package com.example.screenplay.action

import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.Performable
import net.serenitybdd.screenplay.actions.Click

open class LookUpAllUnsuccessfulOutcomes : Performable {
  override fun <T : Actor> performAs(actor: T) {
    actor.attemptsTo(Click.on(".ToggleSideMenu"))
    actor.attemptsTo(Click.on(".traceErrors"))
  }
}