package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;

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

    wire();
  }
}
