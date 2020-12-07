package com.example.mock;

import java.util.Objects;

public class Issue {
  private final String requestBy;
  private boolean done = false;
  private String description;

  public Issue(String requestBy) {
    this.requestBy = requestBy;
  }

  public static Issue by(String requestBy) {
    return new Issue(requestBy);
  }

  public String getRequestBy() {
    return requestBy;
  }

  public void setDone() {
    done = true;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public boolean isDone() {
    return done;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Issue)) return false;
    Issue issue = (Issue) o;
    return done == issue.done &&
        Objects.equals(requestBy, issue.requestBy) &&
        Objects.equals(description, issue.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestBy, done, description);
  }
}

