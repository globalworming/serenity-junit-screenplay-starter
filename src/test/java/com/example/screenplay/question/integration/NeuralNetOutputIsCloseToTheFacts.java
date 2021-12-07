package com.example.screenplay.question.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.neuralnet.domain.Signal;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.question.QuestionWithDefaultSubject;
import lombok.val;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.number.IsCloseTo;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

public class NeuralNetOutputIsCloseToTheFacts extends QuestionWithDefaultSubject<Boolean> {
  @Override
  public Boolean answeredBy(Actor actor) {
    NeuralNet neuralNet = InteractWithNeuralNet.as(actor);
    val inputNeurons = neuralNet.getInputNeurons();
    val outputNeurons = neuralNet.getOutputNeurons();
    val latestOutputs = new ArrayList<Double>();
    for (int i = 0; i < outputNeurons.size(); i++) {
      int atIndex = i;
      outputNeurons.get(atIndex).connect((d) -> latestOutputs.add(atIndex, d));
    }

    neuralNet
        .getFacts()
        .forEach(
            fact -> {
              for (int i = 0; i < fact.getInputs().size(); i++) {
                inputNeurons
                    .get(i)
                    .accept(Signal.builder().strength(fact.getInputs().get(i)).build());
                for (int j = 0; j < fact.getOutputs().size(); j++) {
                  Double expectedOutput = fact.getOutputs().get(j);
                  assertThat(latestOutputs.get(j), IsCloseTo.closeTo(expectedOutput, .1));
                }
              }
            });
    return true;
  }
}
