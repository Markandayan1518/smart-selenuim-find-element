package com.schneider.electric.util;

import com.google.common.collect.Lists;

import com.aventstack.extentreports.Status;
import com.schneider.electric.config.ElementNameConfig;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommonUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(CommonUtil.class.getName());

  public static void highlightClick(WebElement element) {
    String elementName = ElementNameConfig.getName(element);
    try {
      WaiterUtil.waitForElementToBecomeVisible(element, Duration.ofSeconds(2));
      JavaScriptUtil.highlight(element);
      element.click();

      getExtentTest().log(Status.INFO, "highlight and click : " + elementName);
      LOGGER.log(Level.INFO, "highlight and click : {0}", elementName);

    } catch (Exception ex) {
      getExtentTest().log(Status.FAIL, ex.getMessage());
      LOGGER.log(Level.SEVERE, "Failed to highlight and click : " + elementName, ex);
      throw ex;
    }
  }

  public static void highlightClick(By locator) {
    WebElement element = getDriver().findElement(locator);
    highlightClick(element);
  }

  public static String getValue(WebElement element) {
    String elementName = ElementNameConfig.getName(element);
    String value = null;

    try {
      WaiterUtil.waitForElementToBecomeVisible(element, Duration.ofSeconds(2));
      value = StringUtils.defaultString(element.getAttribute("value"));

      if (StringUtils.isEmpty(value)) {
        LOGGER.log(Level.WARNING, "Value of {0} : EMPTY", elementName);
        getExtentTest().log(Status.INFO, "Value of " + elementName + " : EMPTY");

      } else {
        LOGGER.log(Level.INFO, "Value of {0} : '{1}'", new Object[]{elementName, value});
        getExtentTest().log(Status.INFO, "Value of " + elementName + " : '" + value + "'");
      }

    } catch (Exception ex) {
      getExtentTest().log(Status.FAIL, ex.getMessage());
      LOGGER.log(Level.SEVERE, "Failed to fetch the value : " + elementName, ex);
    }

    return value;
  }

  public static void setValueForInputField(WebElement inputElement, String value) {
    String elementName = ElementNameConfig.getName(inputElement);
    String trimmedValue = StringUtils.trimToEmpty(value);

    if (StringUtils.isNotEmpty(trimmedValue)) {
      DriverUtil.populateField(inputElement, trimmedValue);
      getExtentTest().log(Status.INFO, "Typed the " + elementName + " : " + trimmedValue);
      LOGGER.log(Level.INFO, "Typed Value of {0} : '{1}'", new Object[]{elementName, trimmedValue});

    } else {
      getExtentTest().log(Status.SKIP, "Skipped type value of the " + elementName + " due to EMPTY value");
      LOGGER.log(Level.WARNING, "Skipped type value of the {0} due to EMPTY value ", elementName);
    }
  }

  public static void setValueForDropDown(WebElement dropDownElement, String value) {
    String elementName = ElementNameConfig.getName(dropDownElement);
    String trimmedValue = StringUtils.trimToEmpty(value);

    if (StringUtils.isNotEmpty(trimmedValue)) {
      DriverUtil.selectDropdown(dropDownElement, trimmedValue);
      getExtentTest().log(Status.INFO, "Choose the " + elementName + " : " + trimmedValue);
      LOGGER.log(Level.INFO, "Choose Value of {0} : '{1}'", new Object[]{elementName, trimmedValue});

    } else {
      getExtentTest().log(Status.SKIP, "Skipped choose value of the " + elementName + " due to EMPTY value");
      LOGGER.log(Level.WARNING, "Skipped choose value of the {0} due to EMPTY value ", elementName);
    }
  }

  public static void selectMultiValues(WebElement unSelectedElement, WebElement selectedElement, List<String> values, WebElement addButton) {
    String elementName = ElementNameConfig.getName(unSelectedElement);

    if (values != null && !values.isEmpty()) {
      List<String> selectOptions = DropdownUtil.getSelectOptions(selectedElement);
      values.removeAll(selectOptions);
      DropdownUtil.selectDropdownItem(unSelectedElement, values);
      highlightClick(addButton);
      getExtentTest().log(Status.INFO, "Choose the " + elementName + " : " + values);
      LOGGER.log(Level.INFO, "Choose Value of {0} : '{1}'", new Object[]{elementName, values});

    } else {
      getExtentTest().log(Status.SKIP, "Skipped choose value of the " + elementName + " due to EMPTY values");
      LOGGER.log(Level.WARNING, "Skipped choose value of the {0} due to EMPTY value ", elementName);
    }
  }

  public static void selectMultiValues(WebElement unSelectedElement, WebElement selectedElement, String values, WebElement addButton) {
    List<String> list = Arrays.stream(StringUtils.split(values, ','))
        .map(StringUtils::trimToEmpty)
        .filter(StringUtils::isNotEmpty)
        .collect(Collectors.toList());
    selectMultiValues(unSelectedElement, selectedElement, list, addButton);
  }

}
