package com.example.e2e.neuralnet.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.action.WireAllTheNeurons;
import com.example.screenplay.question.integration.AllInputsAreWiredToAllOutputs;
import com.example.screenplay.question.integration.NumberOfWires;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

@Narrative(
    text =
        "we want to be able to build different neural networks, possibly 'on-the-fly'. A useful model could be a neural net that has an input layer, an output layer and optional hidden layers. After building all these layers, we need to wire all the neurons together.")
@RunWith(SerenityRunner.class)
public class BuildingCustomNeuralNetIT {

  Actor actor = Actor.named("tester");
  NeuralNet neuralNet;

  @Before
  public void setUp() {
    neuralNet = new NeuralNet();
    actor.can(new InteractWithNeuralNet(neuralNet));
  }

  @Test
  public void whereTheDefaultIsInspected() {
    actor.should(
        seeThat("number of neurons", a -> neuralNet.size(), is(0L)),
        seeThat("number of input neurons", a -> neuralNet.getInputNeurons().size(), is(0)),
        seeThat("number of output neurons", a -> neuralNet.getOutputNeurons().size(), is(0)),
        seeThat("number of layers", a -> neuralNet.getHiddenLayers().size(), is(0)));
    seeThat("number of wires", a -> neuralNet.getWires().size(), is(0));
  }

  @Test
  // these test names are much nicer with kotlin like
  // fun `where it has one InputNeuron and one OutputNeuron`
  public void whereOneInputAndOneOutputIsWired() {
    givenNeuralNetWithSingleInAndOutput();
    actor.attemptsTo(new WireAllTheNeurons());
    actor.should(seeThat(new NumberOfWires(), is(1)), seeThat(new AllInputsAreWiredToAllOutputs()));
  }

  private void givenNeuralNetWithSingleInAndOutput() {
    Serenity.reportThat(
        "given neural net with single in- and output",
        () -> {
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new Neuron());
        });
  }

  @Test
  public void whereTwoInputAndTwoOutputNeuronsAreWired() {
    givenNeuralNetWithTwoInAndTwoOutputNeurons();
    actor.attemptsTo(new WireAllTheNeurons());
    actor.should(seeThat(new NumberOfWires(), is(4)), seeThat(new AllInputsAreWiredToAllOutputs()));
  }

  private void givenNeuralNetWithTwoInAndTwoOutputNeurons() {
    Serenity.reportThat(
        "given neural net with 2 in- and 2 output neurons",
        () -> {
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new Neuron());
        });
  }

  @Test
  public void whereThereAreHiddenLayersWired() {
    givenNeuralNetWithHiddenLayers();
    actor.attemptsTo(new WireAllTheNeurons());
    actor.should(seeThat(new NumberOfWires(), is(12)));
  }

  private void givenNeuralNetWithHiddenLayers() {
    Serenity.reportThat(
        "given neural net with 4 layers, ",
        () -> {
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addInputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new Neuron());
          neuralNet.addOutputNeuron(new Neuron());
        });
    Serenity.reportThat(
        "and two hidden layers, each 2 neurons",
        () -> {
          neuralNet.addNeuronToLayer(new Neuron(), 1);
          neuralNet.addNeuronToLayer(new Neuron(), 1);
          neuralNet.addNeuronToLayer(new Neuron(), 0);
          neuralNet.addNeuronToLayer(new Neuron(), 0);
        });
  }

  @Test
  public void whereItHasOneInputAndOneOutputNeuron() {
    givenNeuralNetWithSingleInAndOutput();
    actor.should(
        seeThat("number of neurons", a -> neuralNet.size(), is(2L)),
        seeThat("number of input neurons", a -> neuralNet.getInputNeurons().size(), is(1)),
        seeThat("number of output neurons", a -> neuralNet.getOutputNeurons().size(), is(1)),
        seeThat("number of hidden layers", a -> neuralNet.getHiddenLayers().size(), is(0)));
  }
}
