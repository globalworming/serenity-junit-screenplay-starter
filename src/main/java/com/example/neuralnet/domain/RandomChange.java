package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RandomChange {
  private final Adjustable target;
  private final double amount;
}
