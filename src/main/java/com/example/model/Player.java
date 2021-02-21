package com.example.model;

import lombok.Getter;

@Getter
public class Player {
  private final String name;

  Player(String name) {
    this.name = name;
  }
}
