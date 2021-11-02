package com.example.neuralnet.controller;

import com.example.neuralnet.component.NeuralNetwork;
import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.InferenceResults;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NeuralNetController {

  private final NeuralNetwork neuralNetwork;
  private Collection<InferenceResult> mockResults = Arrays.asList(InferenceResult.builder().confidence(.5).label("white").build(), InferenceResult.builder().label("gray").confidence(.6).build());

  @GetMapping("/infer")
  InferenceResults infer(@RequestParam int h, @RequestParam int s, @RequestParam int l) {
    List<InferenceResult> inferenceResults = new ArrayList<>();
    inferenceResults.add(neuralNetwork.infer(0.01d * l));
    inferenceResults.addAll(mockResults);
    return InferenceResults.builder().inferenceResults(inferenceResults).build();
  }

  @GetMapping("/train")
  void train(@RequestParam String label) {
    neuralNetwork.increaseTheWeight();
  }

}
