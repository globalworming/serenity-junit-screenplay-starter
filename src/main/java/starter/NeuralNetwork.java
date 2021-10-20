package starter;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class NeuralNetwork {
  private final Neuron blackDetectingNeuron;

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
