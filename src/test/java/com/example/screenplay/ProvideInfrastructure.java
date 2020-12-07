package com.example.screenplay;

import com.example.mock.Issue;
import com.example.screenplay.ability.ManageInfrastructure;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.thucydides.core.annotations.Step;

import java.util.NoSuchElementException;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ProvideInfrastructure implements Performable {
  private final String name;

  public ProvideInfrastructure(String name) {
    this.name = name;
  }

  public static ProvideInfrastructure forDeveloper(String name) {
    return instrumented(ProvideInfrastructure.class, name);
  }

  @Override
  @Step("{0} provide infrastructure for #name")
  public <T extends Actor> void performAs(T t) {
    Issue issue = ManageInfrastructure.as(t).getService().getIssues().stream()
        .filter(it -> !it.isDone())
        .filter(it -> it.getRequestBy().equals(name))
        .findFirst()
        .orElseThrow(NoSuchElementException::new);
    issue.setDone();
    issue.setDescription("intra3.example.com");
  }
}
