package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.domain.LabeledHslColor;
import com.example.screenplay.ability.AskAndTrainColorDetectingNeuralNetwork;
import com.example.screenplay.action.TrainColorDetectingNeuralNet;
import com.example.screenplay.actor.Memory;
import com.example.screenplay.question.integration.TheHighestConfidence;
import com.example.screenplay.question.integration.TheMostLikelyLabel;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.number.IsCloseTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.example.neuralnet.component.HslColor.BLACK;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(
    text =
        "A neural net that should tell you the color of something. You have to train it first though.")
@RunWith(SerenityRunner.class)
public class ColorDetectingNeuralNetIT {

  Actor actor = Actor.named("tester");

  @Before
  public void setUp() {
    actor.can(new AskAndTrainColorDetectingNeuralNetwork(new LabelHslColorNeuralNet()));
    actor.remember(Memory.NUMBER_OF_TRAINING_ROUNDS, 500);
  }

  @Test
  public void actorCanAskNeuralNet() {
    actor.should(seeThat(TheMostLikelyLabel.of(BLACK), notNullValue()));
  }

  @Test
  public void whenTrainingOnSpecificOutputTheConfidenceGetsVeryHigh() {
    List<LabeledHslColor> trainingData = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      trainingData.add(LabeledHslColor.builder().hslColor(BLACK).label("black").build());
    }

    actor.attemptsTo(TrainColorDetectingNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheHighestConfidence.of(BLACK));
    assertThat(afterTraining, IsCloseTo.closeTo(1., 0.1));
  }
}
