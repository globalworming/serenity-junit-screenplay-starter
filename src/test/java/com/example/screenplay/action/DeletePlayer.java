package com.example.screenplay.action;

public class DeletePlayer {

  private final String name;

  private DeletePlayer(String name) {
    this.name = name;
  }

  public static DeletePlayer named(String name) {
    return new DeletePlayer(name);
  }
}
