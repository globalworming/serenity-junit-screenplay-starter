package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.Action.PAPER;
import static com.example.Action.SCISSORS;
import static com.example.Action.STONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

  private static Stream<Arguments> variants() {
    return Stream.of(
        Arguments.of(STONE, STONE, null),
        Arguments.of(STONE, SCISSORS, STONE),
        Arguments.of(SCISSORS, STONE, STONE),
        Arguments.of(STONE, PAPER, PAPER),
        Arguments.of(PAPER, STONE, PAPER),
        Arguments.of(PAPER, SCISSORS, SCISSORS),
        Arguments.of(SCISSORS, PAPER, SCISSORS)
    );
  }

  @ParameterizedTest
  @MethodSource("variants")
  void winningAction(Action a, Action b, Action winning) {
    assertEquals(winning, Game.winningAction(a, b));
  }
}