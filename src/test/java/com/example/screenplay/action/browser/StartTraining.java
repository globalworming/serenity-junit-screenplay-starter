package com.example.screenplay.action.browser;

import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThan;

public class StartTraining implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    int intendedTrainingRounds = actor.recall(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS);
    long currentTrainingRounds = getCurrentTrainingRounds(actor);
    while (currentTrainingRounds <= intendedTrainingRounds) {
      actor.attemptsTo(Click.on(Target.the("train a few rounds").locatedBy(".e2e-do-train")));
      actor.should(
          eventually(
              seeThat(
                  "number of training rounds",
                  this::getCurrentTrainingRounds,
                  greaterThan(currentTrainingRounds))));
      currentTrainingRounds = getCurrentTrainingRounds(actor);
    }
  }

  private <T extends Actor> Long getCurrentTrainingRounds(T actor) {
    return actor.asksFor(Text.of(".e2e-number-of-training-rounds").asLong());
  }
}
