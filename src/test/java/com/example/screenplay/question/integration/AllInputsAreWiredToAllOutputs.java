package com.example.screenplay.question.integration;

import com.example.neuralnet.domain.Neuron;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.val;
import net.serenitybdd.screenplay.Actor;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class AllInputsAreWiredToAllOutputs extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    val neuralNet = InteractWithNeuralNet.as(actor);
    List<? extends Neuron> inputNeurons = neuralNet.getInputNeurons();
    List<? extends Neuron> outputNeurons = neuralNet.getOutputNeurons();
    for (Neuron inputNeuron : inputNeurons) {
      for (Neuron outputNeuron : outputNeurons) {
        assertThat(
            "there is a wire connecting in and output",
            neuralNet.getWires().stream()
                .anyMatch(
                    it ->
                        it.getSource().equals(inputNeuron) && it.getTarget().equals(outputNeuron)));
      }
    }
    return true;
  }
}
