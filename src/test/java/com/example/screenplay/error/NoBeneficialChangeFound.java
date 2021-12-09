package com.example.screenplay.error;

import net.serenitybdd.core.exceptions.TestCompromisedException;

public class NoBeneficialChangeFound extends TestCompromisedException {
  public NoBeneficialChangeFound(String message) {
    super(message);
  }
}
