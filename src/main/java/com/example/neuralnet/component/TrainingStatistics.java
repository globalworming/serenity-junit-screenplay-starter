package com.example.neuralnet.component;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainingStatistics {

  private List<Double> errors = new ArrayList<>();
// TODO validation set errors and validation set % labeled correctly 

  public void trackError(double d) {
    errors.add(d);
  }
}
