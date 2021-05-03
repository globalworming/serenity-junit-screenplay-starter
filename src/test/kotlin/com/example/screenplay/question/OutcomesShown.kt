package com.example.screenplay.question

import net.serenitybdd.core.pages.WebElementFacade
import net.serenitybdd.screenplay.Actor
import net.serenitybdd.screenplay.targets.Target

class OutcomesShown : QuestionWithDefaultSubject<List<WebElementFacade>>() {
  override fun answeredBy(actor: Actor): List<WebElementFacade> {
    return Target.the("outcomes that can be selected").locatedBy(".selectOutcome").resolveAllFor(actor)
  }
}