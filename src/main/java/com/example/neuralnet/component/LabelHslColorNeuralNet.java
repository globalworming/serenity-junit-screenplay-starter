package com.example.neuralnet.component;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.Neuron;

import java.util.List;

public class LabelHslColorNeuralNet extends ColorDetectingNeuralNetwork {

  public LabelHslColorNeuralNet() {
    addInputNeurons(new Neuron("hue"), new Neuron("saturation"), new Neuron("lightness"));
    addOutputNeurons(new Neuron("black"), new Neuron("gray"), new Neuron("white"));
    getOutputNeurons().forEach(neuron -> neuron.setActivationFunction(ActivationFunction.Sigmoid));
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    wire();
    addFact(List.of(0., 0., .000), List.of(1., 0., 0.));
    addFact(List.of(0., 0., .001), List.of(1., 0., 0.));
    addFact(List.of(0., 0., .01), List.of(1., 0., 0.));
    addFact(List.of(0., 0., .05), List.of(1., 0., 0.));
    addFact(List.of(0., 0., .1), List.of(1., 0., 0.));
    addFact(List.of(0., 0., .5), List.of(.0, 1., 0.));
    addFact(List.of(0., 0., .8), List.of(.0, 1., 0.));
    addFact(List.of(0., 0., .2), List.of(.0, 1., 0.));
    addFact(List.of(0., 0., .7), List.of(.0, 1., 0.));
    addFact(List.of(0., 0., .6), List.of(.0, 1., 0.));
    addFact(List.of(0., 0., 1.), List.of(0., 0., 1.));
    addFact(List.of(0., 0., .97), List.of(0., 0., 1.));
    addFact(List.of(0., 0., .95), List.of(0., 0., 1.));
    addFact(List.of(0., 0., .9), List.of(0., 0., 1.));
    addFact(List.of(0., 0., .87), List.of(0., 0., 1.));
  }

  @Override
  public List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getHue() / 360., hslColor.getSaturation(), hslColor.getLightness());
  }
}
