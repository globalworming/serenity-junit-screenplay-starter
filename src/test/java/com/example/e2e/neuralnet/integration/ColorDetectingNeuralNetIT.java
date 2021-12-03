package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import com.example.neuralnet.component.HslColor;
import com.example.screenplay.LabeledColor;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
import com.example.screenplay.action.TrainNeuralNet;
import com.example.screenplay.question.integration.TheHighestConfidence;
import com.example.screenplay.question.integration.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.number.IsCloseTo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.neuralnet.component.HslColor.BLACK;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@Narrative(
    text =
        "A neural net that should tell you the color of something. You have to train it first though.")
@RunWith(SerenityRunner.class)
public class ColorDetectingNeuralNetIT {

  Actor actor = Actor.named("tester");

  @Before
  public void setUp() throws Exception {
    actor.can(AskAndTrainNeuralNetwork.forColor(new ColorDetectingNeuralNetwork()));
  }

  @Test
  public void actorCanAskNeuralNet() {
    actor.should(seeThat(TheMostLikelyLabel.of(BLACK), notNullValue()));
  }

  @Test
  public void actorTrainsNeuralNet() {
    val trainingData =
        Arrays.asList(
            LabeledColor.builder().hslColor(BLACK).label("black").build(),
            LabeledColor.builder().hslColor(new HslColor(0, 0, .99)).label("black").build(),
            LabeledColor.builder().hslColor(new HslColor(0, 0, .95)).label("black").build());
    val beforeTraining = actor.asksFor(TheHighestConfidence.of(BLACK));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheHighestConfidence.of(BLACK));
    assertThat(afterTraining, greaterThan(beforeTraining));
  }

  @Test
  public void whenTrainingOnSpecificOutputTheConfidenceGetsVeryHigh() {
    List<LabeledColor> trainingData = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      trainingData.add(LabeledColor.builder().hslColor(BLACK).label("black").build());
    }

    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheHighestConfidence.of(BLACK));
    assertThat(afterTraining, IsCloseTo.closeTo(1., 0.1));
  }
}
