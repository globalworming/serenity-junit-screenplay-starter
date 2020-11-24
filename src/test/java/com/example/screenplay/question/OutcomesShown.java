package com.example.screenplay.question;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

import java.util.List;

public class OutcomesShown extends QuestionWithDefaultSubject<List<WebElementFacade>> {

  @Override
  public List<WebElementFacade> answeredBy(Actor actor) {
    return Target.the("outcomes that can be selected").locatedBy(".selectOutcome").resolveAllFor(actor);
  }
}
