package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

@Getter
@Setter
public class Neuron implements Consumer<Signal>, Adjustable {

  private final UUID uuid = UUID.randomUUID();
  private final Sigmoid sigmoid = new Sigmoid();
  private SigmoidFunction sigmoidFunction = sigmoid::value;
  private List<DoubleConsumer> outputConsumers = new ArrayList<>();
  private Map<Wire, Double> inputToStrength = new HashMap<>();
  private double bias;

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
    double sum = inputToStrength.values().stream().mapToDouble(it -> it).sum();
    outputConsumers.forEach(it -> it.accept(sigmoidFunction.apply(sum + bias)));
  }

  public void registerInput(Wire wire) {
    inputToStrength.put(wire, .0);
  }

  @Override
  public void adjust(double d) {
    bias += d;
  }
}
