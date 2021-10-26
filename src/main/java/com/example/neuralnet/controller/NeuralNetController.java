package com.example.neuralnet.controller;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.neuralnet.domain.InferenceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private final NeuralNetwork neuralNetwork;

  @GetMapping("/infer")
  List<InferenceResult> infer(@RequestParam Double color) {
    return Collections.singletonList(neuralNetwork.infer(color));
  }

  @GetMapping("/train")
  void train(@RequestParam String label) {
    neuralNetwork.increaseTheWeight();
  }

}
