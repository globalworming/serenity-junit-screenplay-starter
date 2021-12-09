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
  private final List<ActivationTracker> internalOutputTracker = new ArrayList<>();
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
    val tracker = new ActivationTracker();
    internalOutputTracker.add(tracker);
    outputNeuron.connect(tracker);
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
   * Given specific input we expect some neurons to be very active. Facts are used to check if
   * adjustments to weights and biases are beneficial overall
   */
  public void addFact(List<Double> inputs, List<Double> expectedOutputs) {
    facts.add(Fact.builder().inputs(inputs).outputs(expectedOutputs).build());
  }

  /** @return positive change was applied or false when reverted */
  public boolean trainOnFacts() {
    val currentError = calculateCurrentError();
    val change = decideOnChange();
    applyChange(change);
    val newError = calculateCurrentError();
    if (isPositiveChange(currentError, newError)) {
      return true;
    }
    revertChange(change);
    return false;
  }

  public double calculateCurrentError() {
    if (facts.isEmpty()) {
      throw new IllegalStateException("no facts");
    }
    return facts.stream()
        .mapToDouble(
            fact -> {
              val factualInputs = fact.getInputs();
              for (int i = 0; i < factualInputs.size(); i++) {
                getInputNeurons()
                    .get(i)
                    .accept(Signal.builder().strength(factualInputs.get(i)).build());
              }
              val factualResults = fact.getOutputs();
              double error = 0;
              for (int i = 0; i < factualResults.size(); i++) {
                error +=
                    Math.abs(internalOutputTracker.get(i).getActivation() - factualResults.get(i));
              }
              return error;
            })
        .sum();
  }

  public SingleChangeToWeightOrBias decideOnChange() {
    throw new RuntimeException("TODO");
  }

  public void applyChange(SingleChangeToWeightOrBias change) {
    throw new RuntimeException("TODO");
  }

  public boolean isPositiveChange(double currentCost, double newCost) {
    throw new RuntimeException("TODO");
  }

  public void revertChange(SingleChangeToWeightOrBias change) {
    throw new RuntimeException("TODO");
  }
}
