package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Wire;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ColorDetectingNeuralNetwork extends NeuralNet {
  private final LabeledNeuron blackDetectingNeuron;
  private final LabeledNeuron whiteDetectingNeuron;
  private final LabeledNeuron grayDetectingNeuron;
  private final Wire darknessWire;
  private final Wire lightnessWire;
  private final Wire graynessWire;
  private final Map<String, LabeledNeuron> label2labeledNeuron;

  public ColorDetectingNeuralNetwork() {
    blackDetectingNeuron = new LabeledNeuron("black");
    whiteDetectingNeuron = new LabeledNeuron("white");
    grayDetectingNeuron = new LabeledNeuron("gray");
    darknessWire = Wire.builder().target(blackDetectingNeuron).build();
    lightnessWire = Wire.builder().target(whiteDetectingNeuron).build();
    graynessWire = Wire.builder().target(grayDetectingNeuron).build();
    blackDetectingNeuron.registerInput(darknessWire);
    whiteDetectingNeuron.registerInput(lightnessWire);
    grayDetectingNeuron.registerInput(graynessWire);
    label2labeledNeuron =
        List.of(blackDetectingNeuron, whiteDetectingNeuron, grayDetectingNeuron).stream()
            .collect(Collectors.toMap(LabeledNeuron::getLabel, Function.identity()));
  }

  public synchronized List<InferenceResult> infer(HslColor hslColor) {
    List<InferenceResult> inferenceResults = new ArrayList<>();
    blackDetectingNeuron.connect(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("black").confidence(confidence).build()));
    whiteDetectingNeuron.connect(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("white").confidence(confidence).build()));
    grayDetectingNeuron.connect(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("gray").confidence(confidence).build()));
    double blackness = 1d - hslColor.getLightness();
    double lightness = hslColor.getLightness();
    double grayness = 1d - Math.abs(blackness - lightness);
    darknessWire.accept(blackness);
    lightnessWire.accept(lightness);
    graynessWire.accept(grayness);
    inferenceResults.sort(
        Comparator.comparingDouble(inferenceResult -> -inferenceResult.getConfidence()));
    return inferenceResults;
  }

  public void reward(String label) {
    label2labeledNeuron
        .get(label)
        .getInputToStrength()
        .keySet()
        .forEach(it -> it.setWeight(it.getWeight() + 0.1));
  }
}
