package com.example.neuralnet.component;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
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
}
