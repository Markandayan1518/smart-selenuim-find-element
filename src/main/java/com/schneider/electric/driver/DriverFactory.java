package com.schneider.electric.driver;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverFactory {
  
  private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());
  
  private static List<DriverThread> threadPool = Collections.synchronizedList(new ArrayList<>());
  private static ThreadLocal<DriverThread> thread;

  public static void initDriverThread(WebDriver driver) {
    LOGGER.log(Level.INFO, "CALLED: initDriverThread( {0} )", driver);
    thread = ThreadLocal.withInitial(() -> {
      DriverThread thread = new DriverThread(driver);
      threadPool.add(thread);
      return thread;
    });
  }

  public static WebDriver getDriver() {
    LOGGER.log(Level.INFO, "CALLED: getDriver()");
    return thread.get().getDriver();
  }

  public static void quitDriver() {
    LOGGER.log(Level.INFO, "CALLED: quitDriver()");
    thread.get().quitDriver();
  }

  public static void quitAllDrivers() {
    LOGGER.log(Level.INFO, "CALLED: quitAllDrivers()");
    for (DriverThread driverThread : threadPool) {
      driverThread.quitDriver();
    }
  }

  public static boolean hasThreads() {
    LOGGER.log(Level.INFO, "CALLED: hasThreads()");
    return !threadPool.isEmpty();
  }

}