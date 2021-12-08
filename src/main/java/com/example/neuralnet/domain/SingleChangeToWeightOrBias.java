package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class SingleChangeToWeightOrBias {
  private final boolean changeToWeight;
  private final boolean changeToBias;
  private final UUID target;
  private final double amount;
}
