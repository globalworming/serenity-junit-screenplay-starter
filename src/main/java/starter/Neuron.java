package starter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Neuron {


  private Double weight = 1d;
  private SigmoidFunction sigmoidFunction = it -> {
    throw new RuntimeException("todo");
  };


  public void accept(int it) {
    Double weightedInputValue = applyWeight(it);
    Double valueBetweenZeroAndOne = getSigmoidFunction().apply(weightedInputValue);

  }

  double applyWeight(int input) {
    return input * weight;

  }
}
