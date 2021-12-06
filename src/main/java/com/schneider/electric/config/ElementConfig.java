package com.schneider.electric.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.openqa.selenium.By;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ElementConfig {

  private static final Logger LOGGER = Logger.getLogger(ElementConfig.class.getName());
  private static final Config config = ConfigFactory.parseResources("element.conf");

  public static By getBy(String property) {
    if (StringUtils.isEmpty(property)) {
      LOGGER.log(Level.SEVERE, "Given element property not be EMPTY");
      throw new IllegalArgumentException("property should not be EMPTY");
    }

    if (!config.hasPath(property)) {
      LOGGER.log(Level.SEVERE, "Given element property not have a path");
      throw new IllegalArgumentException(property + " property does not have a path");
    }

    String selectorType = StringUtils.substringAfterLast(property, ".");
    try {
      return (By) MethodUtils.invokeStaticMethod(By.class, selectorType, config.getString(property));
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Failed to fetch element locator form element.config", ex);
    }
    return null;
  }

}
