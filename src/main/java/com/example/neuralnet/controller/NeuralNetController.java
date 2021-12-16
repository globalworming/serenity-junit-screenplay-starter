package com.example.neuralnet.controller;

import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.component.LabelHslColorNeuralNet;
import com.example.neuralnet.component.ModelBuilder;
import com.example.neuralnet.domain.Fact;
import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.LabeledHslColor;
import com.example.neuralnet.domain.NeuralNetModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private LabelHslColorNeuralNet colorDetectingNeuralNetwork = new LabelHslColorNeuralNet();

  @GetMapping("/infer")
  List<InferenceResult> infer(@RequestParam int h, @RequestParam int s, @RequestParam int l) {
    return colorDetectingNeuralNetwork.infer(new HslColor(h, 0.01d * s, 0.01d * l));
  }

  @GetMapping("/remember")
  void rememberFact(
      @RequestParam int h, @RequestParam int s, @RequestParam int l, @RequestParam String label) {
    colorDetectingNeuralNetwork.addFact(
        LabeledHslColor.builder()
            .label(label)
            .hslColor(new HslColor(h, 0.01d * s, 0.01d * l))
            .build());
  }

  @GetMapping("/train")
  void train() {
    int rounds = 500;
    for (int i = 0; i < rounds; i++) {
      colorDetectingNeuralNetwork.trainOnFacts();
    }
  }

  @GetMapping("/facts")
  List<Fact> facts() {
    return colorDetectingNeuralNetwork.getFacts();
  }

  @GetMapping("/currentError")
  double currentError() {
    return colorDetectingNeuralNetwork.calculateCurrentError();
  }

  @GetMapping("/training/errors")
  List<Double> errors() {
    return colorDetectingNeuralNetwork.getTrainingStatistics().getErrors();
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
