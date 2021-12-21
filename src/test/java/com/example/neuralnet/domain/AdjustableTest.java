package com.example.neuralnet.domain;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.number.OrderingComparison.lessThan;

public class AdjustableTest {

  @Test
  public void whereAdjustingNeuronChangesItsBias() {
    val neuron = new Neuron();
    neuron.setBias(0);
    neuron.adjust(-2.);
    assertThat(neuron.getBias(), lessThan(0.));
  }

  @Test
  public void whereAdjustingWireChangesItsWeight() {
    val wire = new Wire(null, null, 0);
    wire.adjust(2.);
    assertThat(wire.getWeight(), greaterThan(0.));
  }
}
