package com.example.screenplay.page;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://example.com/issues")
public class ExampleIssueTracker extends PageObject {

  @DefaultUrl("https://example.com/issues/{1}")
  public class ExampleIssueDetails extends PageObject {}

}

