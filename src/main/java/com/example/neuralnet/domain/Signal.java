package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Signal {
  private final Wire source;
  private final double strength;
}
