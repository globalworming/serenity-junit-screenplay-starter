package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.hamcrest.CoreMatchers;

import java.util.List;

import static java.lang.String.format;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class AddFactsSoGrayResemblesCurveWithPeakAtHalfLightness implements Performable {
  @Override
  public <T extends Actor> void performAs(T actor) {
    NeuralNet grayDetectingNeuralNet = InteractWithNeuralNet.as(actor);
    actor.should(
        seeThat(
            "precondition: neural net has single in and output",
            (a) ->
                grayDetectingNeuralNet.size() == 2
                    && grayDetectingNeuralNet.getInputNeurons().size() == 1
                    && grayDetectingNeuralNet.getOutputNeurons().size() == 1,
            CoreMatchers.is(true)));

    for (int i = 0; i <= 10; i++) {
      double x = i / 10.;
      List<Double> inputs = List.of(x);
      List<Double> expectedOutputs = List.of(f(x));
      Serenity.reportThat(
          format("%s should result in %s", inputs, expectedOutputs),
          () -> grayDetectingNeuralNet.addFact(inputs, expectedOutputs));
    }
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
