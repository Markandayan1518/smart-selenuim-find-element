package com.schneider.electric.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.apache.commons.lang3.StringUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class URLConfig {

  private static final Logger LOGGER = Logger.getLogger(URLConfig.class.getName());
  private static final Config config = ConfigFactory.parseResources("url.conf");

  public static String getURL(String property) {
    if (StringUtils.isEmpty(property)) {
      LOGGER.log(Level.SEVERE, "Given element property not be EMPTY");
      throw new IllegalArgumentException("property should not be EMPTY");
    }

    if (!config.hasPath(property)) {
      LOGGER.log(Level.SEVERE, "Given element property not have a path");
      throw new IllegalArgumentException(property + " property does not have a path");
    }

    return config.getString(property);
  }

}
