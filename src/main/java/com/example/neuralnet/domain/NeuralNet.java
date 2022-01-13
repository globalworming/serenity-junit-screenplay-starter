package com.example.neuralnet.domain;

import com.example.neuralnet.component.TrainingStatistics;
import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

@ToString
@Getter
public class NeuralNet {
  private final Random random = new Random(0);
  private final List<Neuron> inputNeurons = new ArrayList<>();
  private final List<Neuron> outputNeurons = new ArrayList<>();
  private final List<Wire> wires = new ArrayList<>();
  // hidden layer seems like a terrible name. i can see it, can't I?
  private final List<List<Neuron>> hiddenLayers = new ArrayList<>();
  private final List<Fact> facts = new ArrayList<>();
  private List<Adjustable> adjustables = new ArrayList<>();
  private Function<NeuralNet, Double> errorFunction = LossFunction.DEFAULT;
  private TrainingStatistics trainingStatistics = new TrainingStatistics();

  public void addNeuronToLayer(Neuron neuron, int layer) {
    while (hiddenLayers.size() < layer + 1) {
      hiddenLayers.add(new ArrayList<>());
    }
    hiddenLayers.get(layer).add(neuron);
  }

  public void wire() {
    val layers = new ArrayList<List<? extends Neuron>>();
    layers.add(inputNeurons);
    layers.addAll(hiddenLayers);
    layers.add(outputNeurons);

    for (int i = 0; i < layers.size() - 1; i++) {
      for (Neuron layerNeuron : layers.get(i)) {
        for (Neuron nextLayerNeuron : layers.get(i + 1)) {
          wireNeurons(layerNeuron, nextLayerNeuron);
        }
      }
    }
    updateAdjustables();
  }

  protected Wire wireNeurons(Neuron in, Neuron out) {
    val wire =
        Wire.builder()
            .source(in)
            .target(out)
            .weight((random.nextBoolean() ? 1 : -1) * random.nextDouble())
            .build();
    in.connect(wire);
    wires.add(wire);
    return wire;
  }

  protected void updateAdjustables() {
    adjustables = new ArrayList<>(allNeuronsById());
    adjustables.addAll(getWires());
  }

  private List<Adjustable> allNeuronsById() {
    Stream<Neuron> neuronStream =
        concat(
            concat(inputNeurons.stream(), outputNeurons.stream()),
            hiddenLayers.stream().flatMap(Collection::stream));

    return neuronStream.collect(toList());
  }
  // refactor into function
  /** @return positive change was applied or false when reverted */
  public boolean trainOnFacts() {
    val currentError = calculateCurrentError();
    getTrainingStatistics().trackError(currentError);
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
    return errorFunction.apply(this);
  }

  // fixme: amount of change should be in relation to the error i think.
  // makes sense insofar as we can allow big changes when we are in a plain and get more
  // careful when we reach the a point of minimal error where big changes are more likely to be
  // worse? maybe?
  public RandomChange decideOnChange() {
    val amount = random.nextDouble();
    val direction = random.nextBoolean() ? 1 : -1;
    val target =
        // constructs new list.. probably better to just iterate until at right position
        adjustables.get(random.nextInt(adjustables.size()));
    return RandomChange.builder().amount(amount * direction).target(target).build();
  }

  public void applyChange(RandomChange change) {
    change.getTarget().adjust(change.getAmount());
  }

  public boolean isPositiveChange(double currentCost, double newCost) {
    // TODO maybe less or equal? do we want to allow changes that have no effect?
    return newCost < currentCost;
  }

  public void revertChange(RandomChange change) {
    change.getTarget().adjust(-change.getAmount());
  }

  public long size() {
    return inputNeurons.size()
        + outputNeurons.size()
        + hiddenLayers.stream().mapToLong(List::size).sum();
  }

  protected void addInputNeurons(Neuron... neurons) {
    for (Neuron neuron : neurons) {
      addInputNeuron(neuron);
    }
  }

  public void addInputNeuron(Neuron inputNeuron) {
    inputNeuron.setBias(random.nextDouble());
    inputNeurons.add(inputNeuron);
  }

  protected void addOutputNeurons(Neuron... neurons) {
    for (Neuron neuron : neurons) {
      addOutputNeuron(neuron);
    }
  }

  public void addOutputNeuron(Neuron outputNeuron) {
    outputNeuron.setBias(random.nextDouble());
    outputNeurons.add(outputNeuron);
  }

  /**
   * Given specific input we expect some neurons to be very active. Facts are used to check if
   * adjustments to weights and biases are beneficial overall
   */
  public void addFact(List<Double> inputs, List<Double> expectedOutputs) {
    facts.add(Fact.builder().inputs(inputs).outputs(expectedOutputs).build());
  }

  public List<Neuron> getNeurons() {
    return Stream.concat(inputNeurons.stream(), outputNeurons.stream())
        .collect(Collectors.toList());
  }

  public void feedForward() {
    inputNeurons.forEach(Neuron::forward);
    hiddenLayers.forEach(layer -> layer.forEach(Neuron::forward));
    outputNeurons.forEach(Neuron::forward);
  }
}
