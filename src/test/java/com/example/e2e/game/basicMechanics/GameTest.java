package com.example.e2e.game.basicMechanics;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.thucydides.core.annotations.Narrative;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SerenityRunner.class)
@Narrative(text = {""})
public class GameTest {


  @Test
  public void hello() {
    Actor actor = new Actor("hello world");
    actor.should(seeThat("i am alive", a -> a, not(nullValue())));
  }
}
