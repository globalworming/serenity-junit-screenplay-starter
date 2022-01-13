package com.example.screenplay.ability;

import com.example.neuralnet.domain.NeuralNetTrainer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class TrainColorDetectingNeuralNetwork extends Ability {

  private final NeuralNetTrainer neuralNetTrainer;

  public static NeuralNetTrainer as(Actor actor) {
    return Optional.ofNullable(actor.abilityTo(TrainColorDetectingNeuralNetwork.class))
        .orElseThrow()
        .neuralNetTrainer;
  }
}
