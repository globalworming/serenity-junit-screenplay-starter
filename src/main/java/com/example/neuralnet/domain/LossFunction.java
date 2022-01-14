package com.example.neuralnet.domain;

import lombok.val;

import java.util.function.Function;
// TODO loss function regarding validation set
public class LossFunction {
  public static final Function<NeuralNet, Double> DEFAULT =
      (neuralNet) -> {
        if (neuralNet.getFacts().size() == 0) {
          return 0.;
        }
        double sum =
            neuralNet.getFacts().stream()
                .mapToDouble(
                    fact -> {
                      val factualInputs = fact.getInputs();
                      for (int i = 0; i < factualInputs.size(); i++) {
                        neuralNet.getInputNeurons().get(i).accept(factualInputs.get(i));
                      }
                      neuralNet.feedForward();
                      val factualResults = fact.getOutputs();
                      double error = 0;
                      for (int i = 0; i < factualResults.size(); i++) {
                        double activation = neuralNet.getOutputNeurons().get(i).getActivation();
                        error += Math.pow(activation - factualResults.get(i), 2);
                      }
                      return error;
                    })
                .sum();
        return sum / neuralNet.getFacts().size();
      };
}
