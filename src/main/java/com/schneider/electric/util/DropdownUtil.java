package com.schneider.electric.util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DropdownUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(DropdownUtil.class.getName());

  public static void selectDropdownItem(WebElement element, List<String> values) {
    LOGGER.log(Level.INFO, "CALLED: selectDropdown( {0}, {1} )", new Object[]{element, values});

    try {
      Select dropDown = new Select(element);
      values.forEach(dropDown::selectByVisibleText);

    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Failed to selectDropdown", ex);
      throw ex;
    }
  }

  /**
   * Selects item from a dropdown.
   */
  public static void selectDropdownItem(WebElement element, String value) {
    LOGGER.log(Level.INFO, "CALLED: selectDropdown( {0}, {1} )", new Object[]{element, value});

    try {
      Select dropDown = new Select(element);

      if (StringUtils.contains(value, ',')) {
        Arrays.stream(StringUtils.split(value, ','))
            .forEach(dropDown::selectByVisibleText);

      } else {
        dropDown.selectByVisibleText(value);
      }

    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Failed to selectDropdown", ex);
      throw ex;
    }
  }

  public static void selectDropdownItem(By locator, String valueToBeSelected) {
    selectDropdownItem(getDriver().findElement(locator), valueToBeSelected);
  }

  /**
   * Selects item from a dropdown using index of an item.
   */
  public static void selectDropdownItem(By locator, int index) {
    LOGGER.log(Level.INFO, "CALLED: selectDropdownItem()");
    new Select(getDriver().findElement(locator)).selectByIndex(index);
  }

  public static boolean isSelectOptionPresent(By locator, String optionText) {
    LOGGER.log(Level.INFO, "CALLED: isSelectOptionPresent()");
    List<WebElement> selectOptions = new Select(getDriver().findElement(locator)).getOptions();
    for (WebElement selectOption : selectOptions) {
      String currentOptionText = selectOption.getText().trim();
      if (currentOptionText.contains(optionText)) {
        LOGGER.log(Level.INFO, "Option is found");
        return true;
      }
    }
    LOGGER.log(Level.INFO, "Option is NOT found");
    return false;
  }

  public static boolean isSelectEmpty(By locator) {
    LOGGER.log(Level.INFO, "CALLED: isSelectEmpty()");
    Select sel = new Select(getDriver().findElement(locator));
    if (sel.getOptions().size() > 0) {
      LOGGER.log(Level.INFO, "Select is NOT empty");
      return false;
    } else {
      LOGGER.log(Level.INFO, "Select is empty");
      return true;
    }
  }

  /**
   * @return Returns currently selected dropdown value.
   */
  public static String getSelectedItemFromDropdown(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: getSelectedItemFromDropdown( {0} )", element);
    String value = new Select(element)
        .getFirstSelectedOption()
        .getText();
    LOGGER.log(Level.INFO, "Selected option: {0}",  value);
    return value;
  }

  /**
   * @return Returns currently selected dropdown value.
   */
  public static String getSelectedItemFromDropdown(By locator) {
    LOGGER.log(Level.INFO, "CALLED: getSelectedItemFromDropdown( {0} )", locator);
    return getSelectedItemFromDropdown(getDriver().findElement(locator));
  }

  /**
   * @return Returns dropdown items as a list.
   */
  public static List<String> getSelectOptions(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: getSelectOptions( {0} )", element);
    return new Select(element).getOptions()
        .stream()
        .map(WebElement::getText)
        .map(StringUtils::trimToEmpty)
        .collect(Collectors.toList());
  }

  /**
   * @return Returns dropdown items as a list.
   */
  public static List<String> getSelectOptions(By locator) {
    return getSelectOptions(getDriver().findElement(locator));
  }

}