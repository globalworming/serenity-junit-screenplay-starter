package com.example.mock;

import java.util.ArrayList;
import java.util.List;

public class OpsManagement {
  private static OpsManagement instance;

  List<Issue> issues = new ArrayList<>();

  public static OpsManagement getInstance() {
    if (instance == null) {
      instance = new OpsManagement();
    }
    return instance;
  }

  public void add(Issue issue) {
    issues.add(issue);
  }


  public List<Issue> getIssues() {
    return issues;
  }
}
