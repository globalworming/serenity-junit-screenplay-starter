package com.example.neuralnet.component;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import lombok.val;

public class NeuralNetFactory {
  public static NeuralNet buildWithTwoInputsAndTwoOutputs() {
    val neuralNet = new NeuralNet();
    neuralNet.addInputNeuron(LabeledNeuron.builder().build());
    neuralNet.addInputNeuron(LabeledNeuron.builder().build());
    neuralNet.addOutputNeuron(LabeledNeuron.builder().build());
    neuralNet.addOutputNeuron(LabeledNeuron.builder().build());
    neuralNet.wire();
    return neuralNet;
  }

  public static LabelHslColorNeuralNet build(
      ActivationFunction activationFunction, int neuronsPerLayer, int layers) {
    LabelHslColorNeuralNet labelHslColorNeuralNet = new LabelHslColorNeuralNet();
    for (int i = 0; i < layers; i++) {
      for (int j = 0; j < neuronsPerLayer; j++) {
        labelHslColorNeuralNet.addNeuronToLayer(new Neuron(), i);
      }
    }

    labelHslColorNeuralNet.getNeurons().forEach(it -> it.setActivationFunction(activationFunction));
    labelHslColorNeuralNet.wire();
    return labelHslColorNeuralNet;
  }
}
