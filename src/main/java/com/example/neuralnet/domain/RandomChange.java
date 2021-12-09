package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class RandomChange {
  private final UUID target;
  private final double amount;
}
