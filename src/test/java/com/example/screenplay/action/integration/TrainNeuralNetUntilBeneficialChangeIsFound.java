package com.example.screenplay.action.integration;

import com.example.screenplay.ability.TrainNeuralNetwork;
import com.example.screenplay.error.NoBeneficialChangeFound;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

@RequiredArgsConstructor
public class TrainNeuralNetUntilBeneficialChangeIsFound implements Performable {

  private int maxRounds = 1000;

  @Override
  public <T extends Actor> void performAs(T actor) {
    val neuralNetTrainer = TrainNeuralNetwork.as(actor);
    Serenity.reportThat(
        String.format("%s starts a round of training", actor),
        () -> {
          for (int i = 0; i < maxRounds; i++) {
            val before = neuralNetTrainer.calculateCurrentTrainingError();
            neuralNetTrainer.trainRandomlyChangingSingleAdjustable(1);
            val after = neuralNetTrainer.calculateCurrentTrainingError();
            if (neuralNetTrainer.isPositiveChange(before, after)) {
              return;
            }
          }
          throw new NoBeneficialChangeFound("tried for " + maxRounds + "rounds");
        });
  }
}
