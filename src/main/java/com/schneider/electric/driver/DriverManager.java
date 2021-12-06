package com.schneider.electric.driver;

import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.WebDriver;

public class DriverManager {

  private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<WebDriver>();
  private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>();


  public static WebDriver getDriver() {
    return driverThreadLocal.get();
  }

  public static WebDriver setDriver(WebDriver driver) {
    driverThreadLocal.set(driver);
    return driver;
  }

  public static ExtentTest getExtentTest() {
    return extentTestThreadLocal.get();
  }

  public static ExtentTest setExtentTest(ExtentTest extentTest) {
    extentTestThreadLocal.set(extentTest);
    return extentTest;
  }

  public static void clearAll() {
    driverThreadLocal.remove();
    driverThreadLocal = null;

    extentTestThreadLocal.remove();
    extentTestThreadLocal = null;
  }

}
