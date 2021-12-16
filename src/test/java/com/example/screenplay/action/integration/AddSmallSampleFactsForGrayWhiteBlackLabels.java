package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.LabeledNeuron;
import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.hamcrest.CoreMatchers;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class AddSmallSampleFactsForGrayWhiteBlackLabels implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    NeuralNet neuralNet = InteractWithNeuralNet.as(actor);
    actor.should(
        seeThat(
            "precondition: neural net has single input and labels 'black', 'white', 'gray'",
            (a) -> {
              List<LabeledNeuron> outputNeurons = neuralNet.getOutputNeurons();
              return neuralNet.size() == 4
                  && neuralNet.getInputNeurons().size() == 1
                  && outputNeurons.size() == 3
                  && outputNeurons.get(0).getLabel().equals("black")
                  && outputNeurons.get(1).getLabel().equals("gray")
                  && outputNeurons.get(2).getLabel().equals("white");
            },
            CoreMatchers.is(true)));

    Serenity.reportThat(
        "add facts for black clustering at low lightness",
        () -> {
          neuralNet.addFact(List.of(.000), List.of(1., 0., 0.));
          neuralNet.addFact(List.of(.001), List.of(1., 0., 0.));
          neuralNet.addFact(List.of(.01), List.of(1., 0., 0.));
          neuralNet.addFact(List.of(.05), List.of(1., 0., 0.));
          neuralNet.addFact(List.of(.1), List.of(1., 0., 0.));
        });
    Serenity.reportThat(
        "add facts for gray clustering at medium lightness",
        () -> {
          neuralNet.addFact(List.of(.5), List.of(.0, 1., 0.));
          neuralNet.addFact(List.of(.8), List.of(.0, 1., 0.));
          neuralNet.addFact(List.of(.2), List.of(.0, 1., 0.));
          neuralNet.addFact(List.of(.7), List.of(.0, 1., 0.));
          neuralNet.addFact(List.of(.6), List.of(.0, 1., 0.));
        });
    Serenity.reportThat(
        "add facts for white clustering at high lightness",
        () -> {
          neuralNet.addFact(List.of(1.), List.of(0., 0., 1.));
          neuralNet.addFact(List.of(.97), List.of(0., 0., 1.));
          neuralNet.addFact(List.of(.95), List.of(0., 0., 1.));
          neuralNet.addFact(List.of(.9), List.of(0., 0., 1.));
          neuralNet.addFact(List.of(.87), List.of(0., 0., 1.));
        });
  }

  /**
   *
   *
   * <pre>f(x)=-4x²+4x</pre>
   *
   * <pre>1 |    ****   </pre>
   *
   * <pre>  |   *    *  </pre>
   *
   * <pre>  |  *      * </pre>
   *
   * <pre>  | *        *</pre>
   *
   * <pre>  | *        *</pre>
   *
   * <pre>0 -------------</pre>
   *
   * <pre>  0            1</pre>
   */
  private double f(double v) {
    return -4 * (v * v) + 4 * v;
  }
}
