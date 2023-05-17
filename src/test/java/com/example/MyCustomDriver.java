package com.example;

import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyCustomDriver implements DriverSource {

  @Override
  public WebDriver newDriver() {
    System.out.println("new driver call");
    return new ChromeDriver();
  }

  @Override
  public boolean takesScreenshots() {
    return true;
  }
}
