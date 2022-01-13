package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.val;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.concat;

@ToString
@Getter
public class NeuralNet {
  private final Random random = new Random(0);
  private final List<LabeledNeuron> inputNeurons = new ArrayList<>();
  private final List<LabeledNeuron> outputNeurons = new ArrayList<>();
  private final List<Wire> wires = new ArrayList<>();
  private final List<List<Neuron>> hiddenLayers = new ArrayList<>();
  private TreeMap<UUID, Adjustable> uuidToAdjustable = new TreeMap<>();
  private BiFunction<NeuralNet, List<Fact>, Double> errorFunction = ErrorFunction.DEFAULT;

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
    uuidToAdjustable = new TreeMap<>(allNeuronsById());
    getWires().forEach(wire -> uuidToAdjustable.put(wire.getUuid(), wire));
  }

  private Map<UUID, Adjustable> allNeuronsById() {
    Stream<Neuron> neuronStream =
        concat(
            concat(inputNeurons.stream(), outputNeurons.stream()),
            hiddenLayers.stream().flatMap(Collection::stream));

    return new TreeMap<>(neuronStream.collect(toMap(Neuron::getUuid, Function.identity())));
  }

  public long size() {
    return inputNeurons.size()
        + outputNeurons.size()
        + hiddenLayers.stream().mapToLong(List::size).sum();
  }

  protected void addInputNeurons(LabeledNeuron... neurons) {
    for (LabeledNeuron neuron : neurons) {
      addInputNeuron(neuron);
    }
  }

  public void addInputNeuron(LabeledNeuron inputNeuron) {
    inputNeuron.setBias(random.nextDouble());
    inputNeurons.add(inputNeuron);
  }

  protected void addOutputNeurons(LabeledNeuron... neurons) {
    for (LabeledNeuron neuron : neurons) {
      addOutputNeuron(neuron);
    }
  }

  public void addOutputNeuron(LabeledNeuron outputNeuron) {
    outputNeuron.setBias(random.nextDouble());
    outputNeurons.add(outputNeuron);
  }

  public List<Neuron> getNeurons() {
    return Stream.concat(inputNeurons.stream(), outputNeurons.stream())
        .collect(Collectors.toList());
  }
}
