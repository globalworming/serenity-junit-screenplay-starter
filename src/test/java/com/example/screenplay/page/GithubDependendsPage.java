package com.example.screenplay.page;

import net.serenitybdd.screenplay.targets.Target;

public class GithubDependendsPage {

  public static Target NEXT_PAGE_BUTTON =
      Target.the("next page button").locatedBy("//a[contains(text(),'Next')]");
}
