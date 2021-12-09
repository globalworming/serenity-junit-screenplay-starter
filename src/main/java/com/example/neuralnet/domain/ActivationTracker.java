package com.example.neuralnet.domain;

import lombok.Getter;

import java.util.function.DoubleConsumer;

@Getter
public class ActivationTracker implements DoubleConsumer {
  private double activation;

  @Override
  public void accept(double d) {
    activation = d;
  }

  public double getActivation() {
    return activation;
  }
}
