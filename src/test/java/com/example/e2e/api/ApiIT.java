package com.example.e2e.api;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class ApiIT {

  private static Actor tester;

  @BeforeClass
  public static void setUp() {
    tester = new Actor("API tester");
    tester.can(CallAnApi.at("https://postman-echo.com"));
  }

  @Test
  public void whenGettingSomething() {

  }

  @Test
  public void whenPosting() {

  }
}
