package com.example.e2e.neuralnet.integration;

import com.example.e2e.NeuralNetBase;
import com.example.neuralnet.domain.LabeledHslColor;
import com.example.screenplay.action.integration.TrainColorDetectingNeuralNetwork;
import com.example.screenplay.question.integration.color.TheHighestConfidence;
import com.example.screenplay.question.integration.color.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.neuralnet.component.HslColor.BLACK;
import static com.example.neuralnet.component.HslColor.GRAY;
import static com.example.neuralnet.component.HslColor.WHITE;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;

@Narrative(
    text =
        "A neural net that should tell you the color of something. You have to train it first though.")
@RunWith(SerenityRunner.class)
public class ColorDetectingNeuralNetIT extends NeuralNetBase {

  @Test
  public void whereYouCanAskNeuralNet() {
    you.should(seeThat(TheMostLikelyLabel.of(BLACK), notNullValue()));
  }

  @Test
  public void whereTrainerTrainsNeuralNet() {
    val trainingData =
        Arrays.asList(
            LabeledHslColor.builder().hslColor(BLACK).label("black").build(),
            LabeledHslColor.builder().hslColor(GRAY).label("gray").build(),
            LabeledHslColor.builder().hslColor(WHITE).label("white").build());
    val beforeTraining = you.asksFor(TheHighestConfidence.of(BLACK));
    trainer.attemptsTo(TrainColorDetectingNeuralNetwork.onDataSet(trainingData));
    double afterTraining = you.asksFor(TheHighestConfidence.of(BLACK));
    assertThat(afterTraining, lessThan(beforeTraining));
  }

  @Test
  public void whenTrainingOnSpecificOutputTheConfidenceGetsVeryHigh() {
    List<LabeledHslColor> trainingData = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      trainingData.add(LabeledHslColor.builder().hslColor(BLACK).label("black").build());
    }

    trainer.attemptsTo(TrainColorDetectingNeuralNetwork.onDataSet(trainingData));
    double afterTraining = you.asksFor(TheHighestConfidence.of(BLACK));
    assertThat(afterTraining, IsCloseTo.closeTo(1., 0.1));
  }
}
