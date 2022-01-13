package com.example.neuralnet.domain;

import com.example.neuralnet.component.NeuralNetFactory;
import lombok.val;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class FindFlakynessTest {

  @Test
  public void twoNetworksWithTheSameRandomSeedShouldBehaveEqually() {
    val neuralNet = NeuralNetFactory.buildWithTwoInputsAndTwoOutputs();
    val other = NeuralNetFactory.buildWithTwoInputsAndTwoOutputs();
    val both = List.of(neuralNet, other);
    both.forEach(it -> it.addFact(List.of(1., 1.), List.of(1., 1.)));
    assertThat(neuralNet.calculateCurrentError(), IsCloseTo.closeTo(5.88, .1));
    ensureBothHaveEqualError(both);
    ensureBothHaveEqualNeuronBiasAndWireStrenght(both);

    both.forEach(NeuralNet::trainOnFacts);
    both.forEach(NeuralNet::trainOnFacts);
    both.forEach(NeuralNet::trainOnFacts);
    ensureBothHaveEqualError(both);
  }

  private void ensureBothHaveEqualError(List<NeuralNet> both) {
    assertThat(
        both.get(0).calculateCurrentError(),
        IsCloseTo.closeTo(both.get(1).calculateCurrentError(), 0.0001));
  }

  private void ensureBothHaveEqualNeuronBiasAndWireStrenght(List<NeuralNet> both) {
    List<Neuron> neurons = both.get(0).getNeurons();
    for (int i = 0; i < neurons.size(); i++) {
      Neuron neuron = neurons.get(i);
      assertThat(
          both.get(1).getNeurons().get(i).getBias(), IsCloseTo.closeTo(neuron.getBias(), 0.0001));
    }
    List<Wire> wires = both.get(0).getWires();
    for (int i = 0; i < wires.size(); i++) {
      val wire = wires.get(i);
      assertThat(
          both.get(1).getWires().get(i).getWeight(), IsCloseTo.closeTo(wire.getWeight(), 0.0001));
    }
  }
}
