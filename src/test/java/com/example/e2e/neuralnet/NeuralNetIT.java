package com.example.e2e.neuralnet;

import com.example.screenplay.ColorSet;
import com.example.screenplay.ability.AskNeuralNetwork;
import com.example.screenplay.action.TrainNeuralNet;
import com.example.screenplay.question.TheColor;
import io.vavr.collection.List;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;
import starter.NeuralNetwork;
import starter.Neuron;

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
    actor.should(seeThat(TheColor.of(0x00), is("black")));
    actor.should(seeThat(TheColor.of(0xFF), is("white")));
    actor.should(seeThat(TheColor.of(0xA0), is("gray")));
  }

  @Test
  public void actorTrainsNeuralNet() {
    val trainingData = List.of(
        ColorSet.builder().color(0x00).label("black").build(),
        ColorSet.builder().color(0xFF).label("white").build());

    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork(new Neuron())));
    String beforeTraining = actor.asksFor(TheColor.of(0x00));
    actor.attemptsTo(TrainNeuralNet.onDataSet(trainingData));
    String afterTraining = actor.asksFor(TheColor.of(0x00));
    assertThat(beforeTraining, not(is(afterTraining)));
  }
}
