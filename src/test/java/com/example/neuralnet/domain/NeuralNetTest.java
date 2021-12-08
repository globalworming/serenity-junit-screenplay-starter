package com.example.neuralnet.domain;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NeuralNetTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  NeuralNet neuralNet;

  @Test
  public void howTrainingWorks() {
    // given actor can interact with mocked neural net
    MockitoAnnotations.openMocks(this);
    // given neural net is deciding on a change that's not beneficial
    Mockito.when(neuralNet.isPositiveChange(anyDouble(), anyDouble())).thenReturn(Boolean.FALSE);

    // when going through one training
    Mockito.when(neuralNet.trainOnFacts()).thenCallRealMethod();
    neuralNet.trainOnFacts();

    // it will calculate costs and revert changes because not beneficial
    verify(neuralNet, times(2)).calculateCurrentCost();
    verify(neuralNet, times(1)).decideOnChange();
    verify(neuralNet, times(1)).applyChange(any());
    verify(neuralNet, times(1)).isPositiveChange(anyDouble(), anyDouble());
    verify(neuralNet, times(1)).revertChange(any());
  }
}
