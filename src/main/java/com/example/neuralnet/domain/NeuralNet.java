package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class NeuralNet {
  private final List<Neuron> inputNeurons = new ArrayList<>();
  private final List<Neuron> outputNeurons = new ArrayList<>();
  private final List<Wire> wires = new ArrayList<>();
  private final List<List<Neuron>> hiddenLayers = new ArrayList<>();
  private final List<Fact> facts = new ArrayList<>();

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
    for (Neuron inputNeuron : inputNeurons) {
      for (Neuron outputNeuron : outputNeurons) {
        val wire = Wire.builder().source(inputNeuron).target(outputNeuron).build();
        inputNeuron.connect(wire);
        wires.add(wire);
        outputNeuron.registerInput(wire);
      }
    }
  }

  /**
   * Given specific input we expect some labeled neurons to be very active. Facts are used to check
   * if adjustments to weights and biases are beneficial overall
   */
  public void addFact(List<Double> inputs, List<Double> expectedOutputs) {
    facts.add(Fact.builder().inputs(inputs).outputs(expectedOutputs).build());
  }
}
