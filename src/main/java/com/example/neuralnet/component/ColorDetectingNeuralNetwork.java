package com.example.neuralnet.component;

import com.example.neuralnet.domain.*;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColorDetectingNeuralNetwork extends NeuralNet {

  public ColorDetectingNeuralNetwork() {
    val blacknessInput = LabeledNeuron.builder().label("blackness = 1 - lightness").build();
    val blackOutput = LabeledNeuron.builder().label("black").build();
    wireNeurons(blacknessInput, blackOutput);

    val graynessInput =
        LabeledNeuron.builder().label("grayness = | lightess - blackness  |").build();
    val grayOutput = LabeledNeuron.builder().label("gray").build();
    wireNeurons(graynessInput, grayOutput);

    val lightnessInput = LabeledNeuron.builder().label("lightness").build();
    val whiteOutput = LabeledNeuron.builder().label("white").build();
    wireNeurons(lightnessInput, whiteOutput);

    addInputNeurons(blacknessInput, graynessInput, lightnessInput);
    addOutputNeurons(blackOutput, grayOutput, whiteOutput);
    updateAdjustables();
  }

  public synchronized List<InferenceResult> infer(HslColor hslColor) {
    List<Double> inputs = toInputs(hslColor);
    for (int i = 0; i < getInputNeurons().size(); i++) {
      getInputNeurons().get(i).accept(Signal.builder().strength(inputs.get(i)).build());
    }
    return getOutputNeurons().stream()
        .map(
            it ->
                InferenceResult.builder()
                    .confidence(it.getActivation())
                    .label(it.getLabel())
                    .build())
        .collect(Collectors.toList());
  }

  public List<Double> toInputs(HslColor hslColor) {
    double lightness = hslColor.getLightness();
    double blackness = 1d - lightness;
    double grayness = 1d - Math.abs(blackness - lightness);
    return List.of(blackness, grayness, lightness);
  }

  public void addFact(LabeledHslColor labeledHslColor) {
    addFact(
        toInputs(labeledHslColor.getHslColor()),
        getOutputNeurons().stream()
            .map(it -> it.getLabel().equals(labeledHslColor.getLabel()) ? 1. : 0.)
            .collect(Collectors.toList()));
  }
}
