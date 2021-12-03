package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.LabeledNeuron;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ColorDetectingNeuralNetwork {
  private final LabeledNeuron blackDetectingNeuron = new LabeledNeuron("black");
  private final LabeledNeuron whiteDetectingNeuron = new LabeledNeuron("white");
  private final LabeledNeuron grayDetectingNeuron = new LabeledNeuron("gray");

  private final Map<String, LabeledNeuron> label2labeledNeuron =
      List.of(blackDetectingNeuron, whiteDetectingNeuron, grayDetectingNeuron).stream()
          .collect(Collectors.toMap(LabeledNeuron::getLabel, Function.identity()));

  public synchronized List<InferenceResult> infer(HslColor hslColor) {
    List<InferenceResult> inferenceResults = new ArrayList<>();
    blackDetectingNeuron.setOutputConsumer(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("black").confidence(confidence).build()));
    whiteDetectingNeuron.setOutputConsumer(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("white").confidence(confidence).build()));
    grayDetectingNeuron.setOutputConsumer(
        (confidence) ->
            inferenceResults.add(
                InferenceResult.builder().label("gray").confidence(confidence).build()));
    double blackness = 1d - hslColor.getLightness();
    double lightness = hslColor.getLightness();
    double grayness = 1d - Math.abs(blackness - lightness);
    blackDetectingNeuron.accept(blackness);
    whiteDetectingNeuron.accept(lightness);
    grayDetectingNeuron.accept(grayness);
    inferenceResults.sort(
        Comparator.comparingDouble(inferenceResult -> -inferenceResult.getConfidence()));
    return inferenceResults;
  }

  public void reward(String label) {
    label2labeledNeuron.get(label).increaseWeight();
  }
}
