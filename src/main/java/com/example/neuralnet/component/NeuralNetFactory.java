package com.example.neuralnet.component;

import com.example.neuralnet.domain.ActivationFunction;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import lombok.val;

import java.util.List;

public class NeuralNetFactory {
  public static NeuralNet buildWithTwoInputsAndTwoOutputs() {
    val neuralNet = new NeuralNet();
    neuralNet.addInputNeuron(new Neuron());
    neuralNet.addInputNeuron(new Neuron());
    neuralNet.addOutputNeuron(new Neuron());
    neuralNet.addOutputNeuron(new Neuron());
    neuralNet.wire();
    return neuralNet;
  }

  public static NeuralNet buildGamePlayingNeuralNet() {
    val neuralNet = new NeuralNet();
    neuralNet.addInputNeuron(new Neuron("tile-0 danger"));
    neuralNet.addInputNeuron(new Neuron("tile-1 danger"));
    neuralNet.addInputNeuron(new Neuron("tile-2 danger"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-0"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-1"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-2"));
    neuralNet.addOutputNeuron(new Neuron("wait"));
    neuralNet.getNeurons().forEach(neuron -> neuron.setActivationFunction(ActivationFunction.ReLU));
    neuralNet.wire();

    neuralNet.addFact(List.of(1., 1., 1.), List.of(1., 1., 1., 0.));
    // neuralNet.addFact(List.of(1., 1., 0.), List.of(1., 1., 0., 0.));
    // neuralNet.addFact(List.of(1., 0., 1.), List.of(1., 0., 1., 0.));
    // neuralNet.addFact(List.of(0., 1., 1.), List.of(0., 1., 1., 0.));
    neuralNet.addFact(List.of(1., 0., 0.), List.of(1., 0., 0., 0.));
    neuralNet.addFact(List.of(0., 1., 0.), List.of(0., 1., 0., 0.));
    neuralNet.addFact(List.of(0., 0., 1.), List.of(0., 0., 1., 0.));
    neuralNet.addFact(List.of(0.5, 0., 0.), List.of(1., 0., 0., 0.));
    neuralNet.addFact(List.of(0., 0.5, 0.), List.of(0., 1., 0., 0.));
    neuralNet.addFact(List.of(0., 0., 0.5), List.of(0., 0., 1., 0.));
    neuralNet.addFact(List.of(0., 0., 0.), List.of(0., 0., 0., 1.));
    return neuralNet;
  }
}
