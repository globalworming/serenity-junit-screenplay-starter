package starter;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.analysis.function.Sigmoid;

import java.util.function.DoubleConsumer;

@Getter
@Setter
public class Neuron implements DoubleConsumer {


  private final Sigmoid sigmoid = new Sigmoid();
  private Double weight = 1d;
  private SigmoidFunction sigmoidFunction = sigmoid::value;
  private DoubleConsumer outputConsumer;


  @Override
  public void accept(double it) {
    Double weightedInputValue = applyWeight(it);
    Double valueBetweenZeroAndOne = getSigmoidFunction().apply(weightedInputValue);
    outputConsumer.accept(valueBetweenZeroAndOne);
  }

  double applyWeight(double input) {
    return input * weight;

  }
}
