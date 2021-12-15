package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.Neuron;

import java.util.List;

public class LabelHslColorNeuralNet extends ColorDetectingNeuralNetwork {

  public LabelHslColorNeuralNet() {
    addInputNeurons(
        LabeledNeuron.builder().label("hue").build(),
        LabeledNeuron.builder().label("saturation").build(),
        LabeledNeuron.builder().label("lightness").build());
    addOutputNeurons(
        LabeledNeuron.builder().label("black").build(),
        LabeledNeuron.builder().label("gray").build(),
        LabeledNeuron.builder().label("white").build());
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 0);
    wire();
  }

  @Override
  public List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getHue() / 360., hslColor.getSaturation(), hslColor.getLightness());
  }
}
