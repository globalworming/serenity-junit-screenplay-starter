package com.example.e2e.neuralnet;

import com.example.screenplay.ability.AskNeuralNetwork;
import com.example.screenplay.question.TheColor;
import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import starter.NeuralNetwork;
import starter.Neuron;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@Narrative(text = "product that tells blind people the color of something")
@RunWith(SerenityRunner.class)
public class NeuralNetIT {

  @Test
  public void actorCanAskNeuralNet() {
    val actor = Actor.named("tester");
    actor.can(AskNeuralNetwork.forColor(new NeuralNetwork(new Neuron())));
    actor.should(seeThat(TheColor.of(0x00), CoreMatchers.is("black")));
    actor.should(seeThat(TheColor.of(0xFF), CoreMatchers.is("white")));
    actor.should(seeThat(TheColor.of(0xA0), CoreMatchers.is("gray")));
  }
}
