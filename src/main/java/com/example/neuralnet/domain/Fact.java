package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Fact {
  private final List<Double> inputs;
  private final List<Double> outputs;
}
