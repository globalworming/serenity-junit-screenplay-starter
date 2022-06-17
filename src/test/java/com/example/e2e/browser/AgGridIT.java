package com.example.e2e.browser;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@ExtendWith(SerenityJUnit5Extension.class)
public class AgGridIT {

  private OnlineCast cast = new OnlineCast();

  @Test
  public void whenJumpingRight() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(Open.url("https://www.ag-grid.com/example/"));

    WebDriver driver = BrowseTheWeb.as(tester).getDriver();
    driver.findElement(By.cssSelector(".ag-center-cols-container .ag-cell[col-id=\"name\"]") ).click();
    Actions actions = new Actions(driver);
    actions.keyDown(Keys.SHIFT).sendKeys(Keys.END).build().perform();

    //actions.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).build().perform();

  }
}
