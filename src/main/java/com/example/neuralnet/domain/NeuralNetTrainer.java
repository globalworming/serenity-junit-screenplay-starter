package com.example.neuralnet.domain;

import com.example.neuralnet.component.TrainingStatistics;
import lombok.Builder;
import lombok.Getter;
import lombok.val;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

@Builder
@Getter
public class NeuralNetTrainer {
  public final BiFunction<NeuralNet, List<Fact>, Double> errorFunction = ErrorFunction.DEFAULT;
  private final NeuralNet neuralNet;
  private final List<Fact> facts = new ArrayList<>();
  private final TrainingStatistics trainingStatistics = new TrainingStatistics();
  private final Random random = new Random(0);
  private final double learningRate = 1d;

  public void train(int iterations) {
    for (int i = 0; i < iterations; i++) {
      val currentError = calculateCurrentTrainingError();
      trainingStatistics.trackError(currentError);
      val change = decideOnChange();
      applyChange(change);
      val newError = calculateCurrentTrainingError();
      if (!isPositiveChange(currentError, newError)) {
        revertChange(change);
      }
    }
  }

  public double calculateCurrentTrainingError() {
    return errorFunction.apply(neuralNet, facts);
  }

  // fixme: amount of change should be in relation to the error i think.
  // makes sense insofar as we can allow big changes when we are in a plain and get more
  // careful when we reach the a point of minimal error where big changes are more likely to be
  // worse? maybe?
  public RandomChange decideOnChange() {
    val amount = random.nextDouble() * learningRate;
    val direction = random.nextBoolean() ? 1 : -1;
    val target =
        // constructs new list.. probably better to just iterate until at right position
        // TODO could this be the source of unwanted randomness? this keyset should be sorted,
        // right?
        new ArrayList<>(neuralNet.getUuidToAdjustable().keySet())
            .get(random.nextInt(neuralNet.getUuidToAdjustable().keySet().size()));
    return RandomChange.builder().amount(amount * direction).target(target).build();
  }

  public void applyChange(RandomChange change) {
    neuralNet.getUuidToAdjustable().get(change.getTarget()).adjust(change.getAmount());
  }

  public boolean isPositiveChange(double before, double after) {
    // TODO maybe less or equal? do we want to allow changes that have no effect?
    return after < before;
  }

  public void revertChange(RandomChange change) {
    neuralNet.getUuidToAdjustable().get(change.getTarget()).adjust(-change.getAmount());
  }

  public void addFact(List<Double> input, List<Double> expectedOutput) {
    facts.add(Fact.builder().inputs(input).outputs(expectedOutput).build());
  }
}
