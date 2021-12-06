package com.schneider.electric.util;

import com.aventstack.extentreports.ExtentTest;
import com.schneider.electric.driver.DriverManager;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

public class SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(SeleniumUtil.class.getName());

  private static Supplier driverSupplier = DriverManager::getDriver;
  private static Supplier extentTestSupplier = DriverManager::getExtentTest;

  public static Duration defaultTimeout = Duration.ofSeconds(1);
  public static Duration defaultPolling = Duration.ofMillis(500);
  public static Duration defaultSleep = Duration.ofMillis(500);

  /**
   * Sets supplier so utils know where to get the driver instance.
   * @param driverSupplier
   */
  public static void init(Supplier driverSupplier){
    SeleniumUtil.driverSupplier = driverSupplier;
    LOGGER.log(Level.WARNING, "supplier: " + driverSupplier);
  }

  public static WebDriver getDriver(){
    WebDriver driver = (WebDriver) driverSupplier.get();
    LOGGER.log(Level.FINE, "supplier: " + driverSupplier + "; " + driver.toString());
    return driver;
  }

  public static ExtentTest getExtentTest(){
    ExtentTest extentTest = (ExtentTest) extentTestSupplier.get();
    LOGGER.log(Level.FINE, "supplier: " + extentTestSupplier + "; " + extentTest.toString());
    return extentTest;
  }

}