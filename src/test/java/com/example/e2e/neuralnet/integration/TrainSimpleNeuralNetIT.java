package com.example.e2e.neuralnet.integration;

import com.example.e2e.NeuralNetBase;
import com.example.neuralnet.domain.LabeledNeuron;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.action.integration.EstablishFact;
import com.example.screenplay.action.integration.TrainNeuralNetUntilBeneficialChangeIsFound;
import com.example.screenplay.action.integration.TrainNeuralNetwork;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.CurrentError;
import com.example.screenplay.question.integration.ErrorGoesDownOverRounds;
import com.example.screenplay.question.integration.NumberOfTrainingRounds;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.IsCloseTo.closeTo;

@Narrative(
    text =
        "when training a neural net, we set up some 'facts', some intended output given a specific input. every round of training, we'll make a small change to the biases and weights and check if the resulting output is closer to the result we want. If not, revert the change. I couldn't wrap my head around the 'cost function', so I use this naive approach first.")
@RunWith(SerenityRunner.class)
public class TrainSimpleNeuralNetIT extends NeuralNetBase {

  private List<Double> input = List.of(1., 1.);
  private List<Double> expectedOutput = List.of(.25, .75);
  private List<Double> otherExpectedOutput = List.of(1., 1.);

  @Test
  public void whereSmallNeuralNetIsTrainedOnSingleFact() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    trainer.attemptsTo(new EstablishFact(input, expectedOutput));
    trainer.attemptsTo(new TrainNeuralNetwork());
    trainer.should(seeThat(new CurrentError(), closeTo(0, .1)));
  }

  private void givenNeuralNetWithTwoInAndOutputNeuronsWiredUp() {
    Serenity.reportThat(
        "given neural net with 2 in- and 2 output neurons wired with each other",
        () -> {
          val neuralNet = InteractWithNeuralNet.as(you);
          neuralNet.addInputNeuron(LabeledNeuron.builder().build());
          neuralNet.addInputNeuron(LabeledNeuron.builder().build());
          neuralNet.addOutputNeuron(LabeledNeuron.builder().build());
          neuralNet.addOutputNeuron(LabeledNeuron.builder().build());
          neuralNet.wire();
        });
  }

  @Test
  public void whereOneBeneficialChangeShouldReduceTheError() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    trainer.attemptsTo(new EstablishFact(input, expectedOutput));
    val errorBeforeTraining = new CurrentError().answeredBy(trainer);
    trainer.should(seeThat(new CurrentError(), greaterThan(9.)));
    trainer.attemptsTo(new TrainNeuralNetUntilBeneficialChangeIsFound());
    trainer.should(seeThat(new CurrentError(), lessThan(errorBeforeTraining)));
  }

  @Test
  public void whereConflictingFactsFindBeneficialChange() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    trainer.attemptsTo(
        new EstablishFact(input, expectedOutput), new EstablishFact(input, otherExpectedOutput));
    val errorBeforeTraining = trainer.asksFor(new CurrentError());
    trainer.attemptsTo(new TrainNeuralNetUntilBeneficialChangeIsFound());
    trainer.should(seeThat(new CurrentError(), lessThan(errorBeforeTraining)));
  }

  /** also flakey? didn't I just see this fail? */
  @Test
  public void whereConflictingFactsLimitTheDecreaseInError() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    trainer.attemptsTo(
        new EstablishFact(input, expectedOutput), new EstablishFact(input, otherExpectedOutput));
    trainer.should(seeThat(new CurrentError(), IsCloseTo.closeTo(7.6, 0.01)));
    trainer.attemptsTo(new TrainNeuralNetwork());
    // won't get better no matter how many changes you try, there must always be a constant error
    trainer.should(seeThat(new CurrentError(), closeTo(0.15, .01)));
  }

  /** flaky, probably due to floating point arithmetic mess-up */
  @Test
  public void whereTrainingLogsShowErrorGoingDown() {
    givenNeuralNetReadyForTraining();
    trainer.attemptsTo(new TrainNeuralNetwork());
    trainer.should(seeThat(new ErrorGoesDownOverRounds()));
  }

  private void givenNeuralNetReadyForTraining() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    trainer.wasAbleTo(
        new EstablishFact(input, expectedOutput), new EstablishFact(input, otherExpectedOutput));
  }

  @Test
  public void whereWeKeepTrackOfTheNumberOfTrainingRounds() {
    givenNeuralNetReadyForTraining();
    trainer.attemptsTo(new TrainNeuralNetwork());

    int rounds = trainer.recall(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS);
    trainer.should(seeThat(new NumberOfTrainingRounds(), is(rounds)));
  }
}
