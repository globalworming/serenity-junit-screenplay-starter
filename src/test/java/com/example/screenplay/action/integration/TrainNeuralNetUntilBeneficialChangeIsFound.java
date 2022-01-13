package com.example.screenplay.action.integration;

import com.example.neuralnet.domain.NeuralNet;
import com.example.screenplay.ability.InteractWithNeuralNet;
import com.example.screenplay.error.NoBeneficialChangeFound;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

@RequiredArgsConstructor
public class TrainNeuralNetUntilBeneficialChangeIsFound implements Performable {

  int maxRounds = 1000;

  @Override
  public <T extends Actor> void performAs(T actor) {
    NeuralNet neuralNet = InteractWithNeuralNet.as(actor);
    Serenity.reportThat(
        String.format("%s starts a round of training", actor),
        () -> {
          for (int i = 0; i < maxRounds; i++) {
            boolean beneficialChangeMade = neuralNet.trainOnFacts();
            if (beneficialChangeMade) {
              return;
            }
          }
          throw new NoBeneficialChangeFound("tried for " + maxRounds + "rounds");
        });
  }
}
