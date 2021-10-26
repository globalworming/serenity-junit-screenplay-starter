package com.example.neuralnet.controller;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.neuralnet.domain.InferenceResults;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private final NeuralNetwork neuralNetwork;

  @GetMapping("/infer")
  InferenceResults infer(@RequestParam Double color) {
    return InferenceResults.builder().inferenceResults(Collections.singletonList(neuralNetwork.infer(color))).build();
  }

  @GetMapping("/train")
  void train(@RequestParam String label) {
    neuralNetwork.increaseTheWeight();
  }

}
