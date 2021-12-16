package com.example.neuralnet.domain;

import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.function.Function;

public interface ActivationFunction extends Function<Double, Double> {
  ActivationFunction ReLU = (x) -> Math.max(0, x);
  ActivationFunction Sigmoid = new Sigmoid()::value;
  ActivationFunction DEFAULT = ReLU;
}
