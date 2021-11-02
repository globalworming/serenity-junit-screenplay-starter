package com.example.e2e.integration;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.screenplay.ColorSet;
import com.example.screenplay.ability.AskNeuralNetwork;
import com.example.screenplay.action.TrainNeuralNet;
import com.example.screenplay.question.integration.TheConfidence;
import com.example.screenplay.question.integration.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(text = "product that tells blind people the color of something. we want to train a neural net to do that")
@RunWith(SerenityRunner.class)
public class NeuralNetIT {

  private final double BLACK = .0;
  Actor actor = Actor.named("tester");

  @Test
  public void actorCanAskNeuralNet() {
    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork()));
    actor.should(seeThat(TheMostLikelyLabel.of(0x00), StringEndsWith.endsWith("black")));
    //actor.should(seeThat(TheMostLikelyLabel.of(0xFF), is("white")));
    //actor.should(seeThat(TheMostLikelyLabel.of(0xA0), is("gray")));
  }

  @Test
  public void actorTrainsNeuralNet() {
    val trainingData = Arrays.asList(
        ColorSet.builder().color(BLACK).label("black").build(),
        ColorSet.builder().color(0.9).label("black").build(),
        ColorSet.builder().color(0.8).label("black").build());

    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork()));
    double beforeTraining = actor.asksFor(TheConfidence.of(BLACK));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheConfidence.of(BLACK));
    assertThat(beforeTraining, not(is(afterTraining)));
  }

  @Test
  public void whenTrainingOnSpecificOutputTheConfidenceGetsVeryHigh() {
    List<ColorSet> trainingData = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      trainingData.add(ColorSet.builder().color(BLACK).label("black").build());
    }

    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork()));

    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheConfidence.of(BLACK));
    assertThat(afterTraining, IsCloseTo.closeTo(1., 0.1));

  }
}
