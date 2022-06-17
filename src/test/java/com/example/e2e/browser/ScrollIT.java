package com.example.e2e.browser;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Narrative;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

@ExtendWith(SerenityJUnit5Extension.class)
public class ScrollIT {

  private OnlineCast cast = new OnlineCast();

  @Test
  public void whenScrollingRight() {
    Actor tester = cast.actorUsingBrowser("chrome").named("tester");
    tester.attemptsTo(Open.url("https://www.ag-grid.com/example/"));

    WebDriver driver = BrowseTheWeb.as(tester).getDriver();
    driver.findElement(By.cssSelector(".ag-center-cols-container .ag-cell[col-id=\"name\"]") ).click();
    Actions actions = new Actions(driver);
    WebElement element = driver.findElement(By.cssSelector(".ag-center-cols-container .ag-cell[col-id=\"name\"]") );
    Actions scrollDown = actions.moveToElement( element );
    scrollDown.keyDown(Keys.SHIFT).sendKeys(Keys.END).build().perform();

    //scrollDown.sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT,Keys.ARROW_RIGHT, Keys.ARROW_RIGHT, Keys.ARROW_RIGHT).build().perform();

  }
}
