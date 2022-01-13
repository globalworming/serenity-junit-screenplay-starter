package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

@Getter
@Setter
@NoArgsConstructor
public class Neuron implements Consumer<Signal>, Adjustable {

  private final UUID uuid = UUID.randomUUID();
  private ActivationFunction activationFunction = ActivationFunction.DEFAULT;
  private List<DoubleConsumer> outputConsumers = new ArrayList<>();
  private Map<Wire, Double> inputToStrength = new HashMap<>();
  private double bias = 0;

  public Neuron(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
  }

  @Override
  public String toString() {
    return String.format("Neuron<%s>", uuid);
  }

  public void connect(DoubleConsumer consumer) {
    outputConsumers.add(consumer);
  }

  @Override
  public void accept(Signal signal) {
    inputToStrength.put(signal.getSource(), signal.getStrength());
    double activation = getCurrentActivation();
    outputConsumers.forEach(it -> it.accept(activation));
  }

  double getCurrentActivation() {
    if (inputToStrength.size() == 0) {
      return activationFunction.apply(bias);
    }

    double sum = inputToStrength.values().stream().mapToDouble(it -> it).sum();
    return activationFunction.apply(sum + bias);
  }

  @Override
  public void adjust(double d) {
    bias += d;
  }
}
