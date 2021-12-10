package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.*;

@ToString
@Getter
public class NeuralNet {
  private final Random random = new Random(0);
  private final List<Neuron> inputNeurons = new ArrayList<>();
  private final List<Neuron> outputNeurons = new ArrayList<>();
  private final List<ActivationTracker> internalOutputTracker = new ArrayList<>();
  private final List<Wire> wires = new ArrayList<>();
  // hidden layer seems like a terrible name. i can see it, can't I?
  private final List<List<Neuron>> hiddenLayers = new ArrayList<>();
  private final List<Fact> facts = new ArrayList<>();
  private final TreeMap<UUID, Adjustable> uuidToAdjustable = new TreeMap<>();

  public void addInputNeuron(Neuron inputNeuron) {
    inputNeurons.add(inputNeuron);
  }

  public void addNeuronToLayer(Neuron neuron, int layer) {
    while (hiddenLayers.size() < layer + 1) {
      hiddenLayers.add(new ArrayList<>());
    }
    hiddenLayers.get(layer).add(neuron);
  }

  public void addOutputNeuron(Neuron outputNeuron) {
    outputNeurons.add(outputNeuron);
    val tracker = new ActivationTracker();
    internalOutputTracker.add(tracker);
    outputNeuron.connect(tracker);
  }

  public void wire() {
    val layers = new ArrayList<List<Neuron>>();
    layers.add(inputNeurons);
    layers.addAll(hiddenLayers);
    layers.add(outputNeurons);

    for (int i = 0; i < layers.size() - 1; i++) {
      for (Neuron layerNeuron : layers.get(i)) {
        for (Neuron nextLayerNeuron : layers.get(i + 1)) {
          val wire = Wire.builder().source(layerNeuron).target(nextLayerNeuron).build();
          layerNeuron.connect(wire);
          wires.add(wire);
          nextLayerNeuron.registerInput(wire);
          uuidToAdjustable.put(wire.getUuid(), wire);
          uuidToAdjustable.put(nextLayerNeuron.getUuid(), nextLayerNeuron);
        }
        uuidToAdjustable.put(layerNeuron.getUuid(), layerNeuron);
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

  public RandomChange decideOnChange() {
    val amount = random.nextDouble();
    val direction = random.nextBoolean() ? 1 : -1;
    val target =
        // constructs new list.. probably better to just iterate until at right position
        new ArrayList<>(uuidToAdjustable.keySet())
            .get(random.nextInt(uuidToAdjustable.keySet().size()));
    return RandomChange.builder().amount(amount * direction).target(target).build();
  }

  public void applyChange(RandomChange change) {
    uuidToAdjustable.get(change.getTarget()).adjust(change.getAmount());
  }

  public boolean isPositiveChange(double currentCost, double newCost) {
    // TODO maybe less or equal? do we want to allow changes that have no effect?
    return newCost < currentCost;
  }

  public void revertChange(RandomChange change) {
    uuidToAdjustable.get(change.getTarget()).adjust(-change.getAmount());
  }

  public long size() {
    return inputNeurons.size()
        + outputNeurons.size()
        + hiddenLayers.stream().mapToLong(List::size).sum();
  }
}
