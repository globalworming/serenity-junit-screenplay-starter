package com.example.neuralnet.domain;

import lombok.val;

import java.util.List;
import java.util.function.BiFunction;
// TODO loss function regarding validation set
/**
 * Let's check wikipedia for Loss Function:
 *
 * <p>In mathematical optimization and decision theory, a loss function or cost function (sometimes
 * also called an error function) is a function that maps an event or values of one or more
 * variables onto a real number intuitively representing some "cost" associated with the event. An
 * optimization problem seeks to minimize a loss function. An objective function is either a loss
 * function or its opposite (in specific domains, variously called a reward function, a profit
 * function, a utility function, a fitness function, etc.), in which case it is to be maximized.
 *
 * <p>Ok, ambiguous terminology. Let's say this is an Error Function and we usually try to minimize
 * the error.
 */
public class ErrorFunction {

  public static final BiFunction<NeuralNet, List<Fact>, Double> DEFAULT =
      (neuralNet, facts) -> {
        if (facts.isEmpty()) {
          return 0.;
        }
        return facts.stream()
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
                        error += Math.pow(activation - factualResults.get(i), 2);
                      }
                      return error;
                    })
                .sum()
            / facts.size();
      };
}
