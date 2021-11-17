package com.example.screenplay.question.integration;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.neuralnet.domain.InferenceResult;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
public class TheInferenceResult extends QuestionWithDefaultSubject<InferenceResult> {

  private final int color;

  public static TheInferenceResult forThe(int color) {
    return new TheInferenceResult(color);
  }

  @Override
  public InferenceResult answeredBy(Actor actor) {
    NeuralNetwork network = AskAndTrainNeuralNetwork.as(actor);
    return network.infer(color);
  }
}
