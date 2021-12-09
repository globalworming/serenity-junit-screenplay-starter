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

public class NeuralNetTrainingTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  NeuralNet neuralNet;

  @Test
  public void whereChangeIsNotBeneficial() {
    // given actor can interact with mocked neural net
    MockitoAnnotations.openMocks(this);
    // given neural net is deciding on a change that's not beneficial
    Mockito.when(neuralNet.isPositiveChange(anyDouble(), anyDouble())).thenReturn(false);

    // when going through one training
    Mockito.when(neuralNet.trainOnFacts()).thenCallRealMethod();
    neuralNet.trainOnFacts();

    // it will apply change, calculate errors and revert changes because not beneficial
    verify(neuralNet, times(2)).calculateCurrentError();
    verify(neuralNet, times(1)).decideOnChange();
    verify(neuralNet, times(1)).applyChange(any());
    verify(neuralNet, times(1)).isPositiveChange(anyDouble(), anyDouble());
    verify(neuralNet, times(1)).revertChange(any());
  }

  @Test
  public void whereChangeIsBeneficial() {
    // given actor can interact with mocked neural net
    MockitoAnnotations.openMocks(this);
    // given neural net is deciding on a change that's beneficial
    Mockito.when(neuralNet.isPositiveChange(anyDouble(), anyDouble())).thenReturn(true);

    // when going through one training
    Mockito.when(neuralNet.trainOnFacts()).thenCallRealMethod();
    neuralNet.trainOnFacts();

    // it will apply change, calculate errors and not revert
    verify(neuralNet, times(2)).calculateCurrentError();
    verify(neuralNet, times(1)).decideOnChange();
    verify(neuralNet, times(1)).applyChange(any());
    verify(neuralNet, times(1)).isPositiveChange(anyDouble(), anyDouble());
    verify(neuralNet, times(0)).revertChange(any());
  }
}
