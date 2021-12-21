package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Signal;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ColorDetectingNeuralNetwork extends NeuralNet {

  public synchronized List<InferenceResult> infer(HslColor hslColor) {
    List<Double> inputs = toInputs(hslColor);
    for (int i = 0; i < getInputNeurons().size(); i++) {
      getInputNeurons().get(i).accept(Signal.builder().strength(inputs.get(i)).build());
    }
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

  public static List<Double> toInputs(HslColor hslColor) {
    return List.of(hslColor.getHue() / 360., hslColor.getSaturation(), hslColor.getLightness());
  }
}
