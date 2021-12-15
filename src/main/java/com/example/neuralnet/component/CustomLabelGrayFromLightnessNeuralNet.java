package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;

import java.util.List;

public class CustomLabelGrayFromLightnessNeuralNet extends ColorDetectingNeuralNetwork {

  public CustomLabelGrayFromLightnessNeuralNet() {
    addInputNeurons(LabeledNeuron.builder().label("lightness").build());
    addOutputNeurons(LabeledNeuron.builder().label("gray").build());
  }

  @Override
  public List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getLightness());
  }
}
