package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.UUID;
import java.util.function.DoubleConsumer;

@Getter
@Setter
public class Neuron implements DoubleConsumer {

  private final UUID uuid = UUID.randomUUID();
  private final Sigmoid sigmoid = new Sigmoid();
  private Double weight = 0d;
  private SigmoidFunction sigmoidFunction = sigmoid::value;
  private DoubleConsumer outputConsumer = (it) -> {};

  @Override
  public void accept(double it) {
    Double weightedInputValue = applyWeight(it);
    Double valueBetweenZeroAndOne = getSigmoidFunction().apply(weightedInputValue);
    outputConsumer.accept(valueBetweenZeroAndOne);
  }

  public double applyWeight(double input) {
    return input * weight;
  }

  public void increaseWeight() {
    weight += 0.1;
  }
}
