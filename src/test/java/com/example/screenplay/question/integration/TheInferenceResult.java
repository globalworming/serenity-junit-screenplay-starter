package com.example.screenplay.question.integration;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.NeuralNetwork;
import com.example.neuralnet.domain.InferenceResult;
import com.example.screenplay.ability.AskAndTrainNeuralNetwork;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

@RequiredArgsConstructor
public class TheInferenceResult extends QuestionWithDefaultSubject<List<InferenceResult>> {

  private final HslColor hslColor;

  public static TheInferenceResult forInput(HslColor hslColor) {
    return new TheInferenceResult(hslColor);
  }

  @Override
  public List<InferenceResult> answeredBy(Actor actor) {
    NeuralNetwork network = AskAndTrainNeuralNetwork.as(actor);
    return network.infer(hslColor);
  }
}
