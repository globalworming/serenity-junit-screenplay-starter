package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import lombok.val;

public class NeuralNetFactory {
  public static NeuralNet buildWithTwoInputsAndTwoOutputs() {
    val neuralNet = new NeuralNet();
    neuralNet.addInputNeuron(new Neuron());
    neuralNet.addInputNeuron(new Neuron());
    neuralNet.addOutputNeuron(new LabeledNeuron("out 1"));
    neuralNet.addOutputNeuron(new LabeledNeuron("out 2"));
    neuralNet.wire();
    return neuralNet;
  }
}
