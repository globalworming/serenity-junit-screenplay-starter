package com.example.e2e.neuralnet;

import com.example.neuralnet.domain.NeuralNetwork;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ColorSet;
import com.example.screenplay.ability.AskNeuralNetwork;
import com.example.screenplay.action.TrainNeuralNet;
import com.example.screenplay.question.TheColorLabel;
import com.example.screenplay.question.TheConfidence;
import io.vavr.collection.List;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.core.StringEndsWith;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@Narrative(text = "product that tells blind people the color of something. we want to train a neural net to do that")
@RunWith(SerenityRunner.class)
public class NeuralNetIT {

  Actor actor = Actor.named("tester");


  @Test
  public void actorCanAskNeuralNet() {
    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork(new Neuron())));
    actor.should(seeThat(TheColorLabel.of(0x00), StringEndsWith.endsWith("black")));
    //actor.should(seeThat(TheColorLabel.of(0xFF), is("white")));
    //actor.should(seeThat(TheColorLabel.of(0xA0), is("gray")));
  }

  @Test
  public void actorTrainsNeuralNet() {
    double thisIsVeryBlack = 1;
    val trainingData = List.of(
        ColorSet.builder().color(thisIsVeryBlack).label("black").build(),
        ColorSet.builder().color(0.9).label("black").build(),
        ColorSet.builder().color(0.8).label("black").build());

    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork(new Neuron())));
    double beforeTraining = actor.asksFor(TheConfidence.of(thisIsVeryBlack));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    double afterTraining = actor.asksFor(TheConfidence.of(thisIsVeryBlack));
    assertThat(beforeTraining, not(is(afterTraining)));
  }
}
