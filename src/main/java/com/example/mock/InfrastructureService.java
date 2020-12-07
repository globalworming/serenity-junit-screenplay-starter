package com.example.mock;

import java.util.List;
import java.util.stream.Collectors;

public class InfrastructureService {

  private static InfrastructureService instance;

  public static InfrastructureService getInstance() {
    if (instance == null) {
      instance = new InfrastructureService();
    }
    return instance;
  }

  public void requestNewServer(String requestBy) {
    OpsManagement.getInstance().add(Issue.by(requestBy));
  }

  public List<Issue> getIssues(String byMe) {
    return OpsManagement.getInstance().getIssues().stream()
        .filter(it -> it.getRequestBy().equals(byMe))
        .collect(Collectors.toList());
  }
}
