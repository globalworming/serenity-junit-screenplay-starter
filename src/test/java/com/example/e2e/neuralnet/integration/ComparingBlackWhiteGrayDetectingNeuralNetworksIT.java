package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.NeuralNetTrainer;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithColorDetectingNeuralNet;
import com.example.screenplay.action.integration.AddSmallSampleFactsForGrayWhiteBlackLabels;
import com.example.screenplay.action.integration.TrainColorDetectingNeuralNetwork;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.color.TheMostLikelyLabel;
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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

@Narrative(
    text = {
      "Just given a hsl-color lightness, labeling some color as 'gray' isn't as trivial as I though. So let's compare a few setups and their training effectiveness, like: how small does the error get after a given amount of training rounds. (TODO training time?) What's the error in the end."
    })
@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComparingBlackWhiteGrayDetectingNeuralNetworksIT {

  Actor you = Actor.named("you");
  Actor trainer = Actor.named("trainer");
  int rounds = 10000;
  LabelHslColorNeuralNet neuralNet = null;
  NeuralNetTrainer neuralNetTrainer = null;

  @Before
  public void setUp() {
    neuralNet = new LabelHslColorNeuralNet();
    you.can(new InteractWithColorDetectingNeuralNet(neuralNet));
    trainer.remember(Memory.DEFAULT_NUMBER_OF_TRAINING_ROUNDS, rounds);
    neuralNetTrainer = NeuralNetTrainer.builder().neuralNet(neuralNet).build();
    trainer.can(
        new com.example.screenplay.ability.TrainColorDetectingNeuralNetwork(neuralNetTrainer));
    trainer.attemptsTo(new AddSmallSampleFactsForGrayWhiteBlackLabels());
  }

  /** the default build, only one sigmoid in- and one output */
  @Test
  public void noHiddenNeuronsAllSigmoid() {
    Serenity.reportThat("with default configuration", () -> neuralNet.wire());

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(
        new com.example.screenplay.action.integration.TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }

  private void reportAndCheckErrorReduction(Double errorBeforeTraining, Double errorAfterTraining) {
    Serenity.reportThat(
        errorReductionHappenedAndExpectedError(errorBeforeTraining, errorAfterTraining),
        () -> {
          assertThat(errorAfterTraining, lessThan(errorBeforeTraining));
          assertThat(errorAfterTraining, lessThan(0.06));
        });
  }

  private void sanityCheckInferenceResults() {
    Serenity.reportThat(
        "then sanity check results",
        () ->
            you.should(
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
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
          neuralNet.wire();
        });

    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    trainer.attemptsTo(new TrainColorDetectingNeuralNetwork());
    val errorAfterTraining = neuralNetTrainer.calculateCurrentTrainingError();
    reportAndCheckErrorReduction(errorBeforeTraining, errorAfterTraining);
    sanityCheckInferenceResults();
  }
}
