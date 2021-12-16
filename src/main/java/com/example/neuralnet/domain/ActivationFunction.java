package com.example.neuralnet.domain;

import java.util.function.Function;

public interface ActivationFunction extends Function<Double, Double> {
  ActivationFunction ReLU = (x) -> Math.max(0, x);
  ActivationFunction Sigmoid = (x) -> Math.max(0, x);
}
