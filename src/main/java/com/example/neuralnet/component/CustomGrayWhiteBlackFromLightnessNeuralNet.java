package com.example.neuralnet.component;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.Neuron;

import java.util.List;

public class CustomGrayWhiteBlackFromLightnessNeuralNet extends ColorDetectingNeuralNetwork {

  public CustomGrayWhiteBlackFromLightnessNeuralNet() {
    Neuron lightness = new Neuron("lightness");
    lightness.setActivationFunction(ActivationFunction.Sigmoid);
    addInputNeurons(lightness);
    addOutputNeurons(new Neuron("black"));
    addOutputNeurons(new Neuron("gray"));
    addOutputNeurons(new Neuron("white"));
    getOutputNeurons().forEach(it -> it.setActivationFunction(ActivationFunction.Sigmoid));
  }

  @Override
  public List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getLightness());
  }
}
