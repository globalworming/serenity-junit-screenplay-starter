package com.example.screenplay.error;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class TargetDirectoryNotSet extends TestCompromisedException {
  public TargetDirectoryNotSet(String message) {
    super(message);
  }
}
