package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.Neuron;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NeuralNetwork {
  private final Neuron blackDetectingNeuron = new Neuron();
  private final Neuron whiteDetectingNeuron = new Neuron();
  private final Neuron grayDetectingNeuron = new Neuron();

  public List<InferenceResult> infer(HslColor hslColor) {
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

  public void increaseTheWeight() {
    blackDetectingNeuron.increaseWeight();
  }
}
