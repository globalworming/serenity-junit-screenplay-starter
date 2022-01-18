package com.example.neuralnet.component;

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

  public static NeuralNet buildGamePlayingNeuralNetThatTrainedSomeRounds() {
    NeuralNet neuralNet = buildGamePlayingNeuralNet();

    neuralNet.addFact(List.of(1., 1., 1., 5.), List.of(0., 0., 0., 0., 1.));
    neuralNet.addFact(List.of(0., 0., 0., 1.), List.of(0., 0., 0., 1., 0.));
    neuralNet.addFact(List.of(1., 1., 1., 1.), List.of(1., 1., 1., 0., 0.));
    neuralNet.addFact(List.of(0., 1., 1., 1.), List.of(0., 1., 1., 0., 0.));
    neuralNet.addFact(List.of(1., 0., 1., 1.), List.of(1., 0., 1., 0., 0.));
    neuralNet.addFact(List.of(1., 1., 0., 1.), List.of(1., 1., 0., 0., 0.));
    neuralNet.addFact(List.of(1., 0., 0., 0.), List.of(1., 0., 0., .0, 0.));
    neuralNet.addFact(List.of(0., 1., 0., 0.), List.of(0., 1., 0., .0, 0.));
    neuralNet.addFact(List.of(0., 0., 1., 0.), List.of(0., 0., 1., .0, 0.));
    neuralNet.addFact(List.of(.5, .5, .5, 5.), List.of(1., 1., 1., 0., 0.));
    trainSomeRounds(neuralNet);
    return neuralNet;
  }

  private static NeuralNet buildGamePlayingNeuralNet() {
    val neuralNet = new NeuralNet();
    neuralNet.addInputNeuron(new Neuron("tile-0 danger"));
    neuralNet.addInputNeuron(new Neuron("tile-1 danger"));
    neuralNet.addInputNeuron(new Neuron("tile-2 danger"));
    neuralNet.addInputNeuron(new Neuron("difficulty"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-0"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-1"));
    neuralNet.addOutputNeuron(new Neuron("click .tile-2"));
    neuralNet.addOutputNeuron(new Neuron("click .increase-difficulty"));
    neuralNet.addOutputNeuron(new Neuron("click .decrease-difficulty"));
    for (int i = 0; i < 10; i++) {
      neuralNet.addNeuronToLayer(new Neuron(), 0);
    }

    neuralNet.wire();
    return neuralNet;
  }

  private static void trainSomeRounds(NeuralNet neuralnet) {
    for (int i = 0; i < 500_000; i++) {
      neuralnet.trainOnFacts();
    }
  }
}
