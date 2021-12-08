package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.action.EstablishFact;
import com.example.screenplay.action.integration.TrainNeuralNet;
import com.example.screenplay.question.integration.NeuralNetOutputIsCloseToTheFacts;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

@Narrative(
    text =
        "when training a neural net, we set up some 'facts', some intended output given a specific input. every round of training, we'll make a small change to the biases and weights and check if the resulting output is closer to the result we want. If not, revert the change. I couldn't wrap my head around the 'cost function', so I use this naive approach first.")
@RunWith(SerenityRunner.class)
public class TrainSimpleNeuralNetIT {

  private Actor actor = Actor.named("tester");
  private NeuralNet neuralNet;
  private List<Double> input = List.of(1., 1.);
  private List<Double> expectedOutput = List.of(.25, .75);

  @Before
  public void setUp() {
    neuralNet = new NeuralNet();
    actor.can(new InteractWithNeuralNet(neuralNet));
  }

  @Test
  public void whereSmallNeuralNetIsTrained() {
    givenNeuralNetWithTwoInAndOutputNeuronsWiredUp();
    actor.attemptsTo(new EstablishFact(input, expectedOutput));
    actor.attemptsTo(new TrainNeuralNet());
    actor.should(seeThat(new NeuralNetOutputIsCloseToTheFacts()));
  }

  private void givenNeuralNetWithTwoInAndOutputNeuronsWiredUp() {
    Serenity.reportThat(
        "given neural net with 2 in- and 2 output neurons wired with each other",
        () -> {
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new LabeledNeuron("out 1"));
          neuralNet.addOutputNeuron(new LabeledNeuron("out 2"));
          neuralNet.wire();
        });
  }
}
