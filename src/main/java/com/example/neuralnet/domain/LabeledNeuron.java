package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LabeledNeuron extends Neuron {

  private final String label;
  private double activation;

  @Override
  public void accept(Signal signal) {
    super.accept(signal);
    activation = getCurrentActivation();
  }
}
