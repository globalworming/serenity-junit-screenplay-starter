package com.example.neuralnet.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.function.DoubleConsumer;

/** forwards signals to neurons, applies weight */
@Getter
@Setter
@Builder
public class Wire implements DoubleConsumer {

  private final UUID uuid = UUID.randomUUID();
  private final Neuron source;
  private final Neuron target;
  private double weight;

  @Override
  public void accept(double d) {
    target.accept(Signal.builder().source(this).strength(d * weight).build());
  }

  @Override
  public String toString() {
    return "Wire{" + "uuid=" + uuid + '}';
  }
}
