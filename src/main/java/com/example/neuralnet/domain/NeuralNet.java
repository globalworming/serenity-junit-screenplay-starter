package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class NeuralNet {
  private final List<Neuron> inputNeurons = new ArrayList<>();
  private final List<Neuron> outputNeurons = new ArrayList<>();
  private final List<List<Neuron>> hiddenLayers = new ArrayList<>();

  public long size() {
    return inputNeurons.size()
        + outputNeurons.size()
        + hiddenLayers.stream().mapToLong(List::size).sum();
  }

  public void addInputNeuron(Neuron inputNeuron) {
    inputNeurons.add(inputNeuron);
  }

  public void addOutputNeuron(Neuron outputNeuron) {
    outputNeurons.add(outputNeuron);
  }

  public void wire() {
    inputNeurons.forEach(it -> it.setOutputConsumer(getOutputNeurons().get(0)));
  }
}
