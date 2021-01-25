package com.example.screenplay.action;

import com.example.Action;
import com.example.screenplay.ability.PlayAGame;
import com.example.screenplay.actor.Memory;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PicksAction implements Performable {

  private final Action action;

  public PicksAction(Action action) {
    this.action = action;
  }


  public static PicksAction stone() {
    return getInstrumented(Action.STONE);
  }

  private static PicksAction getInstrumented(Action action) {
    return instrumented(PicksAction.class, action);
  }

  public static PicksAction scissors() {
    return getInstrumented(Action.SCISSORS);
  }

  public static PicksAction paper() {
    return getInstrumented(Action.PAPER);
  }

  @Step("{0} picks #action")
  @Override
  public <T extends Actor> void performAs(T actor) {
    String gameName = actor.recall(Memory.GAME_NAME);
    PlayAGame.as(actor).getService().pickAction(gameName, actor.getName(), action);
  }
}
