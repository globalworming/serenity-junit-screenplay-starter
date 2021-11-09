package com.example.screenplay.action.browser;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class RewardFor implements Performable {
  private final String label;

  public RewardFor(String label) {
    this.label = label;
  }

  @Override
  public void performAs(Actor actor) {
    Target doReward =
        Target.the("do reward label " + label).locatedBy(".e2e-do-reward-label-" + label);
    actor.attemptsTo(WaitUntil.the(doReward, isEnabled()));
    doReward.resolveFor(actor).click();
  }
}
