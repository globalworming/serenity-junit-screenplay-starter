package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class InferenceResult {

  String label;
  private double confidence;

}
