package com.example.neuralnet.component;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainingStatistics {

  private List<Double> errors = new ArrayList<>();

  public void trackError(double d) {
    errors.add(d);
  }
}
