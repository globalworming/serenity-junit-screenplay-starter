package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.CustomGrayWhiteBlackFromLightnessNeuralNet;
import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import com.example.screenplay.action.WireAllTheNeurons;
import com.example.screenplay.action.integration.AddSmallSampleFactsForGrayWhiteBlackLabels;
import com.example.screenplay.action.integration.EnsureErrorIsBelowSixPercent;
import com.example.screenplay.action.integration.TrainNeuralNetForManyRounds;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.CurrentError;
import com.example.screenplay.question.integration.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static java.lang.String.format;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

@Narrative(
    text = {
      "Just given a hsl-color lightness, labeling some color as 'gray' isn't as trivial as I though. So let's compare a few setups and their training effectiveness, like: how small does the error get after a given amount of training rounds. (TODO training time?) What's the error in the end."
    })
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparingBlackWhiteGrayDetectingNeuralNetworksIT {

  Actor actor = Actor.named("tester");
  int rounds = 50000;
  CustomGrayWhiteBlackFromLightnessNeuralNet neuralNet =
      new CustomGrayWhiteBlackFromLightnessNeuralNet();

  @Before
  public void setUp() {
    actor.can(new AskAndTrainColorDetectingNeuralNetwork(neuralNet));
    actor.remember(Memory.NUMBER_OF_TRAINING_ROUNDS, rounds);
    actor.attemptsTo(new AddSmallSampleFactsForGrayWhiteBlackLabels());
  }

  /** the default build, only one sigmoid in- and one output */
  @Test
  public void noHiddenNeuronsAllSigmoid() {
    Serenity.reportThat(
        "with default configuration", () -> actor.attemptsTo(new WireAllTheNeurons()));

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  private void reportAndCheckErrorReduction(Double errorBeforeTraining, Double errorAfterTraining) {
    Serenity.reportThat(
        errorReductionHappenedAndExpectedError(errorBeforeTraining, errorAfterTraining),
        () -> actor.attemptsTo(new EnsureErrorIsBelowSixPercent()));
  }

  private void sanityCheckInferenceResults() {
    Serenity.reportThat(
        "then sanity check results",
        () ->
            actor.should(
                seeThat(TheMostLikelyLabel.of(HslColor.BLACK), is("black")),
                seeThat(TheMostLikelyLabel.of(HslColor.WHITE), is("white")),
                seeThat(TheMostLikelyLabel.of(HslColor.GRAY), is("gray"))));
  }

  private String errorReductionHappenedAndExpectedError(
      double errorBeforeTraining, double errorAfterTraining) {
    return format(
        "after %s training rounds: error from <%s> reduced to <%s>, we would like to see something ~0.05",
        rounds, errorBeforeTraining, errorAfterTraining);
  }

  @Test
  public void noHiddenNeuronsAllReLu() {
    Serenity.reportThat(
        "with all neurons set to ReLU",
        () -> {
          neuralNet.getNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.ReLU));
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void oneHiddenLayerOfTwoNeurons() {
    Serenity.reportThat(
        "with setting up 2 hidden sigmoids",
        () -> {
          neuralNet.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
          neuralNet.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void oneHiddenLayerOfSixSigmoids() {
    Serenity.reportThat(
        "when setting up 6 hidden Sigmoid neurons",
        () -> {
          for (int i = 0; i < 6; i++) {
            neuralNet.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
          }
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void oneHiddenLayerOfTwoReluNeurons() {
    Serenity.reportThat(
        "when setting up 2 hidden ReLU neurons",
        () -> {
          neuralNet.addNeuronToLayer(new Neuron(), 0);
          neuralNet.addNeuronToLayer(new Neuron(), 0);
          neuralNet.getNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.ReLU));
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void oneHiddenLayerOfSixReluNeurons() {
    Serenity.reportThat(
        "when setting up 6 hidden ReLU neurons",
        () -> {
          for (int i = 0; i < 6; i++) {
            neuralNet.addNeuronToLayer(new Neuron(), 0);
          }
          neuralNet.getNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.ReLU));
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void twoHiddenLayersOfFourReluNeuronsEach() {
    Serenity.reportThat(
        "when setting up 2 hidden layers 4 ReLU neurons each",
        () -> {
          for (int i = 0; i < 4; i++) {
            neuralNet.addNeuronToLayer(new Neuron(), 0);
            neuralNet.addNeuronToLayer(new Neuron(), 1);
          }
          neuralNet.getNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.ReLU));
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  @Test
  public void twoHiddenLayersOfFourSigmoidNeuronsEach() {
    Serenity.reportThat(
        "when setting up 2 hidden layers 4 Sigmoid neurons each",
        () -> {
          for (int i = 0; i < 4; i++) {
            neuralNet.addNeuronToLayer(new Neuron(), 0);
            neuralNet.addNeuronToLayer(new Neuron(), 1);
          }
          neuralNet.getNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.ReLU));
          actor.attemptsTo(new WireAllTheNeurons());
        });

    val errorBeforeTraining = new CurrentError().answeredBy(actor);
    actor.attemptsTo(new TrainNeuralNetForManyRounds());
    val errorAfterTraining = new CurrentError().answeredBy(actor);
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }
}
