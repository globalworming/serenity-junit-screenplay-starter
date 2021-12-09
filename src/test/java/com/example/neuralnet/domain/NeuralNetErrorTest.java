package com.example.neuralnet.domain;

import com.example.neuralnet.component.NeuralNetFactory;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NeuralNetErrorTest {

  @Test
  public void whenCalculatingErrorWithoutFacts() {
    val neuralNet = NeuralNetFactory.buildWithTwoInputsAndTwoOutputs();
    Assert.assertThrows(
        "without facts to compare to, we cannot make any statement about the error",
        IllegalStateException.class,
        neuralNet::calculateCurrentError);
  }

  @Test
  public void whenCalculatingErrorForSingleFact() {
    val neuralNet = NeuralNetFactory.buildWithTwoInputsAndTwoOutputs();
    // with default weight 0, all neurons will emit 0.5
    // we sum up errors for every neuron
    // so an error around 1 would be expected
    neuralNet.addFact(List.of(1., 1.), List.of(1., 1.));
    assertThat(neuralNet.calculateCurrentError(), is(1.));
  }

  @Test
  public void whenCalculatingErrorForMultipleFacts() {
    val neuralNet = NeuralNetFactory.buildWithTwoInputsAndTwoOutputs();

    neuralNet.addFact(List.of(1., 1.), List.of(1., 1.));
    neuralNet.addFact(List.of(.0, .0), List.of(.0, .0));
    assertThat(neuralNet.calculateCurrentError(), is(2.));
  }
}
