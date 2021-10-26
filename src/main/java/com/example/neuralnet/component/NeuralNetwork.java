package com.example.neuralnet.component;

import com.example.neuralnet.domain.InferenceResult;
import com.example.neuralnet.domain.Neuron;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class NeuralNetwork {
  private final Neuron blackDetectingNeuron = new Neuron();

  public InferenceResult infer(double color) {
    AtomicReference<Double> result = new AtomicReference<>(.0);
    blackDetectingNeuron.setOutputConsumer(result::set);
    blackDetectingNeuron.accept(color);
    return InferenceResult.builder().label("black").confidence(result.get()).build();
  }

  public void increaseTheWeight() {
    blackDetectingNeuron.increaseWeight();
  }
}
