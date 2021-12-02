package com.example.neuralnet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LabeledNeuron extends Neuron {

  private final String label;
}
