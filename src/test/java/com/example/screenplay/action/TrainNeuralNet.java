package com.example.screenplay.action;

import com.example.screenplay.ColorSet;
import com.example.screenplay.question.TheColor;
import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import static net.serenitybdd.screenplay.Tasks.instrumented;

@AllArgsConstructor
public class TrainNeuralNet implements Performable {

  private final List<ColorSet> trainingData;

  public static Performable onDataSet(List<ColorSet> trainingData) {
    return instrumented(TrainNeuralNet.class, trainingData);
  }

  @Override
  public <T extends Actor> void performAs(T actor) {
    ColorSet colorSet = trainingData.get(0);
    String label = actor.asksFor(TheColor.of(colorSet.getColor()));
    if (label.equals(colorSet.getLabel())) {
      // good result, strengthen the weights
    }

  }
}
