package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.LabeledHslColor;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Signal;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ColorDetectingNeuralNetwork extends NeuralNet {

  public void addFact(LabeledHslColor labeledHslColor) {
    addFact(
        toInputs(labeledHslColor.getHslColor()),
        getOutputNeurons().stream()
            .map(it -> it.getLabel().equals(labeledHslColor.getLabel()) ? 1. : 0.)
            .collect(Collectors.toList()));
  }

  public abstract List<Double> toInputs(HslColor hslColor);

  public synchronized List<InferenceResult> infer(HslColor hslColor) {
    inputColor(hslColor);
    feedForward();
    return getOutputNeurons().stream()
        .sorted((n1, n2) -> -Double.compare(n1.getActivation(), n2.getActivation()))
        .map(
            it ->
                InferenceResult.builder()
                    .confidence(it.getActivation())
                    .label(it.getLabel())
                    .build())
        .collect(Collectors.toList());
  }

  private void inputColor(HslColor hslColor) {
    List<Double> inputs = toInputs(hslColor);
    for (int i = 0; i < getInputNeurons().size(); i++) {
      getInputNeurons().get(i).accept(Signal.builder().strength(inputs.get(i)).build());
    }
  }
}
