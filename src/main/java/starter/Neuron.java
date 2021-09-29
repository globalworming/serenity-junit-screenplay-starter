package starter;

import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class Neuron {

  private Function<Double, Double> sigmoidFunction = (inputValue) -> {
    throw new RuntimeException("todo, return something betrween 0 and 1");
  };

  private Double strength = Double.valueOf(1d);

  private Consumer<Integer> input = (it) -> {
    throw new RuntimeException("todo");
  };
  private int inputValue;

  public void accept(int inputValue) {
    input.accept(inputValue);
    Double weightedInputValue = applyStrenght(this.inputValue);
    Double valueBetweenZeroAndOne = sigmoidFunction.apply(weightedInputValue);
  }

  public Double applyStrenght(int input) {
    throw new RuntimeException("todo");
  }
}
