package com.example.e2e.neuralnet.gui;

import com.example.e2e.NeuralNetBase;
import com.example.screenplay.action.browser.TrainNeuralNet;
import com.example.screenplay.domain.LabeledColor;
import com.example.screenplay.question.browser.NumberOfFacts;
import com.example.screenplay.question.browser.TheConfidence;
import com.example.screenplay.question.browser.TheMostLikelyLabel;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(
    text =
        "product that tells blind people the color of something. we want to train a neural net to do that")
@RunWith(SerenityRunner.class)
public class NeuralNetIT extends NeuralNetBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    actor.attemptsTo(Open.url("http://localhost:3000"));
    actor.attemptsTo(Click.on(".e2e-do-reset"));
  }

  @Test
  public void whenTrainingOnTwoFacts() {
    val trainingData =
        List.of(
            LabeledColor.builder().color("#FFFFFF").label("white").build(),
            LabeledColor.builder().color("#000000").label("black").build());
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    actor.should(seeThat(TheMostLikelyLabel.of("#FFFFFF"), is("white")));
    actor.should(seeThat(TheMostLikelyLabel.of("#000000"), is("black")));
    // actor.should(seeThat(TheMostLikelyLabel.of(0xA0), is("gray")));
  }

  @Test
  public void whenTrainingOnThreeFacts() {
    val trainingData =
        List.of(
            LabeledColor.builder().color("#FFFFFF").label("white").build(),
            LabeledColor.builder().color("#808080").label("gray").build(),
            LabeledColor.builder().color("#000000").label("black").build());
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    actor.should(seeThat(TheMostLikelyLabel.of("#FFFFFF"), is("white")));
    actor.should(seeThat(TheMostLikelyLabel.of("#808080"), is("gray")));
    actor.should(seeThat(TheMostLikelyLabel.of("#000000"), is("black")));
  }

  @Test
  public void actorCanSeeFactsAndError() {
    LabeledColor c = LabeledColor.builder().color("#000000").label("black").build();
    val trainingData = List.of(c, c);
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    actor.should(eventually(seeThat(new NumberOfFacts(), is(2))));
    // not true for cross entropy
    // actor.should(seeThat(new NewFactsIncreaseTheError()));
  }

  @Test
  public void actorTrainsNeuralNet() {
    LabeledColor c = LabeledColor.builder().color("#000000").label("black").build();
    val trainingData = List.of(c);
    double beforeTraining = actor.asksFor(TheConfidence.of("black"));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheConfidence.of("black"));
    assertThat(beforeTraining, not(is(afterTraining)));
  }
}
