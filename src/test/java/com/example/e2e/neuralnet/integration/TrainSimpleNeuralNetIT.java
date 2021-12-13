package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.action.EstablishFact;
import com.example.screenplay.action.integration.TrainNeuralNetForManyRounds;
import com.example.screenplay.action.integration.TrainNeuralNetUntilBeneficialChangeIsFound;
import com.example.screenplay.question.integration.CurrentError;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.IsCloseTo.closeTo;

@Narrative(
    text =
        "when training a neural net, we set up some 'facts', some intended output given a specific input. every round of training, we'll make a small change to the biases and weights and check if the resulting output is closer to the result we want. If not, revert the change. I couldn't wrap my head around the 'cost function', so I use this naive approach first.")
@RunWith(SerenityRunner.class)
public class TrainSimpleNeuralNetIT {

  private Actor actor = Actor.named("tester");
  private NeuralNet neuralNet;
  private List<Double> input = List.of(1., 1.);
  private List<Double> expectedOutput = List.of(.25, .75);
  private List<Double> otherExpectedOutput = List.of(1., 1.);

  @Before
  public void setUp() {
    neuralNet = new NeuralNet();
    actor.can(new InteractWithNeuralNet(neuralNet));
  }

  @Test
  public void whereSmallNeuralNetIsTrainedOnSingleFact() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    actor.attemptsTo(new EstablishFact(input, expectedOutput));
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    actor.should(seeThat(new CurrentError(), closeTo(0, .1)));
  }

  private void givenNeuralNetWithTwoInAndOutputNeuronsWiredUp() {
    Serenity.reportThat(
        "given neural net with 2 in- and 2 output neurons wired with each other",
        () -> {
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
    actor.attemptsTo(new EstablishFact(input, expectedOutput));
    val errorBeforeTraining = .5;
    actor.should(seeThat(new CurrentError(), is(errorBeforeTraining)));
    actor.attemptsTo(new TrainNeuralNetUntilBeneficialChangeIsFound());
    actor.should(seeThat(new CurrentError(), lessThan(errorBeforeTraining)));
  }

  @Test
  public void whereConflictingFactsFindBeneficialChange() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    actor.attemptsTo(
        new EstablishFact(input, expectedOutput), new EstablishFact(input, otherExpectedOutput));
    val errorBeforeTraining = actor.asksFor(new CurrentError());
    actor.attemptsTo(new TrainNeuralNetUntilBeneficialChangeIsFound());
    actor.should(seeThat(new CurrentError(), lessThan(errorBeforeTraining)));
  }

  @Test
  public void whereConflictingFactsLimitTheDecreaseInError() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    actor.attemptsTo(
        new EstablishFact(input, expectedOutput), new EstablishFact(input, otherExpectedOutput));
    actor.should(seeThat(new CurrentError(), is(1.5)));
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    // won't get better no matter how many changes you try
    actor.should(seeThat(new CurrentError(), closeTo(1., .01)));
  }
}
