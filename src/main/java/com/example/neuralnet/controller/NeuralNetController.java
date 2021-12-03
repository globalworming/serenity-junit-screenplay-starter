package com.example.neuralnet.controller;

import com.example.neuralnet.component.ColorDetectingNeuralNetwork;
import com.example.neuralnet.component.HslColor;
import com.example.neuralnet.domain.InferenceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private final ColorDetectingNeuralNetwork colorDetectingNeuralNetwork;

  @GetMapping("/infer")
  List<InferenceResult> infer(@RequestParam int h, @RequestParam int s, @RequestParam int l) {
    return colorDetectingNeuralNetwork.infer(new HslColor(h, 0.01d * s, 0.01d * l));
  }

  @GetMapping("/train")
  void train(@RequestParam String label) {
    colorDetectingNeuralNetwork.reward(label);
  }
}
