package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.DoubleConsumer;

@Getter
@Setter
@NoArgsConstructor
public class Neuron implements DoubleConsumer, Adjustable {

  private final UUID uuid = UUID.randomUUID();
  private String label;
  private double activation;
  private ActivationFunction activationFunction = ActivationFunction.DEFAULT;
  private List<DoubleConsumer> outputConsumers = new ArrayList<>();
  private List<Double> signals = new ArrayList<>();
  private double bias = 0;

  public Neuron(ActivationFunction activationFunction) {
    this.activationFunction = activationFunction;
  }

  public Neuron(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return String.format("Neuron<%s>", uuid);
  }

  public void connect(DoubleConsumer consumer) {
    outputConsumers.add(consumer);
  }

  @Override
  public void accept(double signal) {
    signals.add(signal);
  }

  public void forward() {
    refreshActivation();
    signals.clear();
    outputConsumers.forEach(it -> it.accept(activation));
  }

  private void refreshActivation() {
    double sum = signals.stream().mapToDouble(it -> it).sum();
    activation = activationFunction.apply(sum + bias);
  }

  @Override
  public void adjust(double d) {
    bias += d;
  }
}
