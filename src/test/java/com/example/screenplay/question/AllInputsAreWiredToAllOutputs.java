package com.example.screenplay.question;

import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeuralNet;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.core.IsIterableContaining;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;

public class AllInputsAreWiredToAllOutputs extends QuestionWithDefaultSubject {
  @Override
  public Object answeredBy(Actor actor) {
    val neuralNet = InteractWithNeuralNet.as(actor);
    neuralNet.getInputNeurons().forEach(checkThatItsLinkedToAllOutputNeurons(neuralNet));
    return true;
  }

  @NotNull
  private static Consumer<Neuron> checkThatItsLinkedToAllOutputNeurons(NeuralNet neuralNet) {
    return inputNeuron ->
        Serenity.reportThat(
            String.format("<%s> should be linked to all output neurons", inputNeuron.getUuid()),
            () ->
                neuralNet
                    .getOutputNeurons()
                    .forEach(
                        neuron ->
                            assertThat(
                                inputNeuron.getOutputConsumers(),
                                IsIterableContaining.hasItems(neuron))));
  }
}
