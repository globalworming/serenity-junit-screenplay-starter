package com.example.screenplay.error;

import net.serenitybdd.core.exceptions.CausesCompromisedTestFailure;

// FIXME does not seem to set result like in previous versions of serenity
public class PreconditionFailed extends AssertionError implements CausesCompromisedTestFailure {
  public PreconditionFailed(Object detailMessage) {
    super(detailMessage);
  }

  public PreconditionFailed(String message, Throwable cause) {
    super(message, cause);
  }
}
