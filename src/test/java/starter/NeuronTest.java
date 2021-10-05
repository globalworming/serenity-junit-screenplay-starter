package starter;

import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Narrative(text = "neural nets are made of multiple layers of linked neurons. " +
    "neurons have inputs and corresponding weights. neurons need to calculate and add the inputs " +
    "(they may add a constant bias) and through a sigmoid function produce a value between 0 and 1 as output")
@RunWith(SerenityRunner.class)
public class NeuronTest {

  int black = 0x00;


  @Test
  public void neuronsAcceptInput() {
    val neuron = mock(Neuron.class);
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron accepts input", (a) -> {
      neuron.accept(black);
      return true;
    }));
  }

  @Test
  public void neuronsHaveInputWeight() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an input weight", (a) -> neuron.getWeight() != null));
  }

  @Test
  public void neuronsHaveSigmoidFunction() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an sigmoid function", (a) -> neuron.getSigmoidFunction() != null));
  }

  @Test
  public void whenAcceptingAnInputItsWeightedAndConvertedByTheSigmoidFunction() {
    SigmoidFunction mockSigmoidFunction = mock(SigmoidFunction.class);
    Neuron spy = spy(new Neuron());
    spy.setSigmoidFunction(mockSigmoidFunction);

    double mockWeightedValue = 0.5;

    Mockito.when(spy.applyWeight(black)).thenReturn(mockWeightedValue);
    double mockResult = 0.95;
    Mockito.when(mockSigmoidFunction.apply(mockWeightedValue)).thenReturn(mockResult);


    spy.accept(black);

    verify(spy, times(1)).applyWeight(black);
    verify(mockSigmoidFunction, times(1)).apply(mockWeightedValue);
  }
}