package com.example.neuralnet.component;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.LabeledNeuron;

public class CustomGrayWhiteBlackFromLightnessNeuralNet extends ColorDetectingNeuralNetwork {

  public CustomGrayWhiteBlackFromLightnessNeuralNet() {
    LabeledNeuron lightness = LabeledNeuron.builder().label("lightness").build();
    lightness.setActivationFunction(ActivationFunction.Sigmoid);
    addInputNeurons(lightness);
    addOutputNeurons(LabeledNeuron.builder().label("black").build());
    addOutputNeurons(LabeledNeuron.builder().label("gray").build());
    addOutputNeurons(LabeledNeuron.builder().label("white").build());
    getOutputNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.Sigmoid));
  }
}
