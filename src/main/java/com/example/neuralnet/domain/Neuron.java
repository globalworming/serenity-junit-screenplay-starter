package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class Neuron implements DoubleConsumer {

  private final UUID uuid = UUID.randomUUID();
  private final Sigmoid sigmoid = new Sigmoid();
  private Double weight = 1d;
  private List<Double> weights = new ArrayList<>();
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

  public void accept(List<Double> inputs) {
    do {
      // this feels terrible. thinking of modeling the input differently
      weights.add(1.);
    } while (inputs.size() > weights.size());

    val sum =
        IntStream.range(0, inputs.size()).mapToObj(i -> inputs.get(i) * weights.get(i))
            .collect(Collectors.toList()).stream()
            .mapToDouble(it -> it)
            .sum();
    outputConsumer.accept(getSigmoidFunction().apply(sum));
  }
}
