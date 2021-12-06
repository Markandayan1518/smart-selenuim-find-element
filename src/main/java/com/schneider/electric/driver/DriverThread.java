package com.schneider.electric.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverThread {

  private static final Logger LOGGER = Logger.getLogger(DriverThread.class.getName());
  
  private WebDriver driver = null;

  public DriverThread(WebDriver driver) {
    this.driver = driver;
  }

  public WebDriver getDriver() {
    LOGGER.log(Level.INFO, "CALLED: getDriver()");
    if (this.driver == null) {
      createDrive();
    }
    return this.driver;
  }

  public void quitDriver() {
    LOGGER.log(Level.INFO, "CALLED: quitDriver()");
    if (this.driver != null) {
      this.driver.quit();
      this.driver = null;
    }
  }

  private void createDrive() {
    LOGGER.log(Level.INFO, "CALLED: createDrive()");
    ChromeOptions options = new ChromeOptions();

    List<String> arguments = new ArrayList<>();
    arguments.add("--no-sandbox");
    arguments.add("--start-maximized");
    arguments.add("--disable-infobars");
    // arguments.add("--disable-gpu");
    options.addArguments(arguments);

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    options.merge(capabilities);

    this.driver = new ChromeDriver(options);
  }

}