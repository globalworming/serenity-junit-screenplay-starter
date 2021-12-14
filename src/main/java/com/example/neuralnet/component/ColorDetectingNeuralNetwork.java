package com.example.neuralnet.component;

import com.example.neuralnet.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class ColorDetectingNeuralNetwork extends NeuralNet {

  public ColorDetectingNeuralNetwork() {

    addInputNeurons(
        LabeledNeuron.builder().label("hue").build(),
        LabeledNeuron.builder().label("saturation").build(),
        LabeledNeuron.builder().label("lightness").build());
    addOutputNeurons(
        LabeledNeuron.builder().label("black").build(),
        LabeledNeuron.builder().label("gray").build(),
        LabeledNeuron.builder().label("white").build());
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 0);
    addNeuronToLayer(new Neuron(), 0);
    wire();
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
    return List.of(hslColor.getHue() / 256., hslColor.getSaturation(), hslColor.getLightness());
  }

  public void addFact(LabeledHslColor labeledHslColor) {
    addFact(
        toInputs(labeledHslColor.getHslColor()),
        getOutputNeurons().stream()
            .map(it -> it.getLabel().equals(labeledHslColor.getLabel()) ? 1. : 0.)
            .collect(Collectors.toList()));
  }
}
