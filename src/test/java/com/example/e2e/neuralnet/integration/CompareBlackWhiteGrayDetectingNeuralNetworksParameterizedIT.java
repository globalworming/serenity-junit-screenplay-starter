package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.component.NeuralNetFactory;
import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.NeuralNetTrainer;
import com.example.neuralnet.domain.TrainingStrategy;
import com.example.screenplay.ability.InteractWithColorDetectingNeuralNet;
import com.example.screenplay.action.integration.AddSmallSampleFactsForGrayWhiteBlackLabels;
import com.example.screenplay.question.integration.color.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.junit.annotations.TestData;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

@Ignore("too slow")
@Narrative(
    text = {
      "Just given a hsl-color lightness, labeling some color as 'gray' isn't as trivial as I though. So let's compare a few setups and their training effectiveness, like: how small does the error get after a given amount of training rounds. (TODO training time?) What's the error in the end."
    })
@RunWith(SerenityParameterizedRunner.class)
public class CompareBlackWhiteGrayDetectingNeuralNetworksParameterizedIT {

  private final String neuralNetDescription;
  private final TrainingStrategy trainingStrategy;
  private int rounds = 100000;
  private Actor you;
  private Actor trainer;
  private NeuralNetTrainer neuralNetTrainer;

  public CompareBlackWhiteGrayDetectingNeuralNetworksParameterizedIT(
      String neuralNetDescription,
      LabelHslColorNeuralNet neuralNet,
      TrainingStrategy trainingStrategy) {
    this.neuralNetDescription = neuralNetDescription;
    neuralNetTrainer = NeuralNetTrainer.builder().neuralNet(neuralNet).build();
    this.trainingStrategy = trainingStrategy;
    you = Actor.named("you");
    trainer = Actor.named("trainer");

    you.can(new InteractWithColorDetectingNeuralNet(neuralNet));
    trainer.can(
        new com.example.screenplay.ability.TrainColorDetectingNeuralNetwork(neuralNetTrainer));
    trainer.attemptsTo(new AddSmallSampleFactsForGrayWhiteBlackLabels());
  }

  @TestData
  public static Collection<Object[]> testData() {
    return Arrays.asList(
        new Object[][] {
          {
            "no hidden neurons, all sigmoid, careful training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 0, 0),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "no hidden neurons, all sigmoid, brute training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 0, 0),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "no hidden neurons, all ReLU, careful training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 0, 0),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "no hidden neurons, all ReLU, brute training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 0, 0),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "6 hidden neurons in one layer, all sigmoid, careful training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 6, 1),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "6 hidden neurons in one layer, all sigmoid, brute training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 6, 1),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "6 hidden neurons, all ReLU, careful training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 6, 1),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "6 hidden neurons, all ReLU, brute training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 6, 1),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "10 hidden neurons in one layer, all sigmoid, careful training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 10, 1),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "10 hidden neurons in one layer, all sigmoid, brute training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 10, 1),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "10 hidden neurons, all ReLU, careful training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 10, 1),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "10 hidden neurons, all ReLU, brute training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 10, 1),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "5 hidden neurons each, 2 layers, all sigmoid, careful training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 5, 2),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "5 hidden neurons each, 2 layers, all sigmoid, brute training",
            NeuralNetFactory.build(ActivationFunction.Sigmoid, 5, 2),
            TrainingStrategy.CHANGE_ALL
          },
          {
            "5 hidden neurons each, 2 layers, all ReLU, careful training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 5, 2),
            TrainingStrategy.CHANGE_ONE
          },
          {
            "5 hidden neurons each, 2 layers, all ReLU, brute training",
            NeuralNetFactory.build(ActivationFunction.ReLU, 5, 2),
            TrainingStrategy.CHANGE_ALL
          }
        });
  }

  /** the default build, only one sigmoid in- and one output */
  @Test
  public void train() {
    Serenity.reportThat("with " + neuralNetDescription, () -> {});
    val errorBeforeTraining = neuralNetTrainer.calculateCurrentTrainingError();
    Serenity.reportThat(
        String.format("train neural net for %s", rounds),
        () -> {
          if (trainingStrategy == TrainingStrategy.CHANGE_ONE) {
            neuralNetTrainer.trainRandomlyChangingSingleAdjustable(rounds);
          } else {
            neuralNetTrainer.trainRandomlyChangingAllAdjustables(rounds);
          }
        });
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
}
