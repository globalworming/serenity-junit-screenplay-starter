package com.example.neuralnet.domain;

import lombok.val;

import java.util.function.Function;

public class ErrorFunction {
  public static final Function<NeuralNet, Double> DEFAULT =
      (neuralNet) ->
          neuralNet.getFacts().stream()
              .mapToDouble(
                  fact -> {
                    val factualInputs = fact.getInputs();
                    for (int i = 0; i < factualInputs.size(); i++) {
                      neuralNet
                          .getInputNeurons()
                          .get(i)
                          .accept(Signal.builder().strength(factualInputs.get(i)).build());
                    }
                    val factualResults = fact.getOutputs();
                    double error = 0;
                    for (int i = 0; i < factualResults.size(); i++) {
                      double activation = neuralNet.getOutputNeurons().get(i).getActivation();
                      error += Math.abs(activation - factualResults.get(i));
                    }
                    return error;
                  })
              .sum();
}
