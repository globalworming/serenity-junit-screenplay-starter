package com.example.neuralnet.controller;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.component.ModelBuilder;
import com.example.neuralnet.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private LabelHslColorNeuralNet colorDetectingNeuralNetwork = new LabelHslColorNeuralNet();
  private NeuralNetTrainer neuralNetTrainer;

  {
    neuralNetTrainer = NeuralNetTrainer.builder().neuralNet(colorDetectingNeuralNetwork).build();
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 0);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
    colorDetectingNeuralNetwork.addNeuronToLayer(new Neuron(ActivationFunction.Sigmoid), 1);
  }

  @GetMapping("/infer")
  List<InferenceResult> infer(@RequestParam int h, @RequestParam int s, @RequestParam int l) {
    return colorDetectingNeuralNetwork.infer(new HslColor(h, 0.01d * s, 0.01d * l));
  }

  @GetMapping("/remember")
  void rememberFact(
      @RequestParam int h, @RequestParam int s, @RequestParam int l, @RequestParam String label) {
    neuralNetTrainer.addFact(
        colorDetectingNeuralNetwork.toInputs(new HslColor(h, s, l)),
        colorDetectingNeuralNetwork.getOutputNeurons().stream()
            .map(it -> it.getLabel().equals(label) ? 1. : 0.)
            .collect(Collectors.toList()));
  }

  @GetMapping("/train")
  void train() {
    neuralNetTrainer.trainRandomlyChangingSingleAdjustable(1000);
  }

  @GetMapping("/facts")
  List<Fact> facts() {
    return neuralNetTrainer.getFacts();
  }

  @GetMapping("/currentError")
  double currentError() {
    return ErrorFunction.DEFAULT.apply(colorDetectingNeuralNetwork, neuralNetTrainer.getFacts());
  }

  @GetMapping("/training/errors")
  List<Double> errors() {
    return neuralNetTrainer.getTrainingStatistics().getErrors();
  }

  @GetMapping("/model")
  NeuralNetModel model() {
    return ModelBuilder.build(colorDetectingNeuralNetwork);
  }

  @GetMapping("/reset")
  void reset() {
    colorDetectingNeuralNetwork = new LabelHslColorNeuralNet();
  }
}
