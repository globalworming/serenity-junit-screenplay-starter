package starter;

import lombok.val;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Consumer;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SerenityRunner.class)
public class NeuronTest {

  @Test
  public void neuronsHaveInputs() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an input", (a) -> neuron.getInput() != null));
    actor.should(seeThat("a neurons input is a consumer of color", (a) -> neuron.getInput() instanceof Consumer));
  }

  @Test
  public void neuronsHaveInputWeight() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an input weight", (a) -> neuron.getStrength() != null));
  }

  @Test
  public void neuronsHaveSigmoidFunction() {
    val neuron = new Neuron();
    val actor = Actor.named("tester");
    actor.should(seeThat("a neuron has an sigmoid function", (a) -> neuron.getSigmoidFunction() != null));
  }

  @Test
  public void whenConsumingAnInput() {
    Neuron spy = spy(new Neuron());
    int black = 0x00;
    doNothing().when(spy).accept(any());
    Double mockWeightedValue = mock(Double.class);
    when(spy.applyStrenght(any())).thenReturn(mockWeightedValue);
    spy.accept(black);
    verify(spy, times(1)).getInput().accept(black);
    verify(spy, times(1)).applyStrenght(black);
    verify(spy, times(1)).getSigmoidFunction().apply(mockWeightedValue);
  }
}