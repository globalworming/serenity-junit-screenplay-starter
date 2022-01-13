package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.Neuron;

import java.util.List;

public class LabelGrayFromLightnessNeuralNet extends ColorDetectingNeuralNetwork {

  public LabelGrayFromLightnessNeuralNet() {
    addInputNeurons(LabeledNeuron.builder().label("lightness").build());
    addOutputNeurons(
        LabeledNeuron.builder().label("black").build(),
        LabeledNeuron.builder().label("gray").build(),
        LabeledNeuron.builder().label("white").build());
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 1);
    addNeuronToLayer(new Neuron(), 1);

    for (int i = 0; i <= 10; i++) {
      addFact(List.of(0.1 * i), List.of(0., f(0.1 * i), 0.));
    }
    wire();
  }

  private double f(double v) {
    return -4 * (v * v) + 4 * v;
  }

  @Override
  public List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getLightness());
  }
}
