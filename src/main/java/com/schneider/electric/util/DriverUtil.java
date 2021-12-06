package com.schneider.electric.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DriverUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(DriverUtil.class.getName());

  public static void disableImplicitWaits() {
    LOGGER.log(Level.INFO, "CALLED: disableImplicitWaits()");
    WaiterUtil.disableImplicitWaits();
  }

  public static void enableImplicitWaits(Duration duration) {
    LOGGER.log(Level.INFO, "CALLED: enableImplicitWaits()");
    WaiterUtil.enableImplicitWaits(duration);
  }

  public static String getPageSource() {
    LOGGER.log(Level.INFO, "CALLED: getPageSource()");
    return getDriver().getPageSource();
  }

  public static void deleteAllCookies() {
    LOGGER.log(Level.INFO, "CALLED: deleteAllCookies()");
    getDriver().manage().deleteAllCookies();
  }

  public static void addCookie(Cookie cookie) {
    LOGGER.log(Level.INFO, "CALLED: addCookie()");
    getDriver().manage().addCookie(cookie);
  }

  public static void pageRefresh() {
    LOGGER.log(Level.INFO, "CALLED: pageRefresh()");
    getDriver().navigate().refresh();
  }

  public static String getCurrentUrl() {
    LOGGER.log(Level.INFO, "CALLED: getCurrentUrl()");
    String url = getDriver().getCurrentUrl();
    LOGGER.log(Level.INFO, "The current URL: " + url);
    return url;
  }

  public static void navigateTo(String url) {
    LOGGER.log(Level.INFO, "CALLED: navigateTo( {0} )", url);
    getDriver().get(url);
  }

  public static void navigateBack() {
    LOGGER.log(Level.INFO, "CALLED: navigateBack()");
    getDriver().navigate().back();
  }

  public static void navigateForward() {
    LOGGER.log(Level.INFO, "CALLED: navigateForward()");
    getDriver().navigate().forward();
  }

  public static boolean isAlertPresent() {
    LOGGER.log(Level.INFO, "CALLED: isAlertPresent()");
    try {
      getDriver().switchTo().alert();
      LOGGER.log(Level.INFO, "Alert is present");
      return true;
    } catch (NoAlertPresentException Ex) {
      LOGGER.log(Level.INFO, "Alert is NOT present");
      return false;
    }
  }

  public static String getAlertTextAndAccept() {
    LOGGER.log(Level.INFO, "CALLED: getAlertTextAndAccept()");
    String text = StringUtils.EMPTY;
    try {
      Alert alert = getDriver().switchTo().alert();
      text = alert.getText().trim();
      LOGGER.log(Level.INFO, "Alert found with text: " + text);
      alert.accept();
      LOGGER.log(Level.INFO, "Alert accepted");
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error occurred.", e);
    }
    return text;
  }

  public static String getAlertTextAndDismiss() {
    LOGGER.log(Level.INFO, "CALLED: getAlertTextAndDismiss()");
    String text = StringUtils.EMPTY;
    try {
      Alert alert = getDriver().switchTo().alert();
      LOGGER.log(Level.INFO, "Alert found with text: " + text);
      text = alert.getText();
      alert.dismiss();
      LOGGER.log(Level.INFO, "Alert dismissed");
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Error occurred.", e);
    }
    return text;
  }

  public static boolean waitForAlertAndAccept(Duration timeout) {
    return WaiterUtil.waitForAlertAndAccept(timeout);
  }

  public static boolean isElementStale(WebElement webElement) {
    LOGGER.log(Level.INFO, "CALLED: isElementStale()");
    try {
      webElement.isEnabled();
      LOGGER.log(Level.INFO, "Element is NOT stale");
      return false;
    } catch (StaleElementReferenceException expected) {
      LOGGER.log(Level.INFO, "Element is stale");
      return true;
    }
  }

  public static boolean isElementPresent(By locator) {
    LOGGER.log(Level.INFO, "CALLED: isElementPresent()");
    try {
      getDriver().findElement(locator);
      LOGGER.log(Level.INFO, "Element is present");
      return true;
    } catch (NoSuchElementException e) {
      LOGGER.log(Level.INFO, "Element is not present");
      return false;
    }
  }

  /**
   * Checks if element is present on page within another element.
   *
   * @return Return true if elements is present within the elementToLookWithin, returns false otherwise.
   */
  public static boolean isElementPresent(WebElement elementToLookWithin, By locator) {
    LOGGER.log(Level.INFO, "CALLED: isElementPresent()");
    try {
      elementToLookWithin.findElement(locator);
      LOGGER.log(Level.INFO, "Element is present");
      return true;
    } catch (NoSuchElementException e) {
      LOGGER.log(Level.INFO, "Element is not present");
      return false;
    }
  }

  public static boolean isElementVisible(By locator) {
    LOGGER.log(Level.INFO, "CALLED: isElementVisible()");
    try {
      return getDriver().findElement(locator).isDisplayed();
    } catch (NoSuchElementException e) {
      LOGGER.log(Level.INFO, "Element is NOT visible");
      return false;
    }
  }

  public static boolean isElementVisible(WebElement webElement) {
    LOGGER.log(Level.INFO, "CALLED: isElementVisible()");
    try {
      return webElement.isDisplayed();
    } catch (NoSuchElementException e) {
      LOGGER.log(Level.INFO, "Element is NOT visible");
      return false;
    }
  }

  /**
   * Checks if elements are present on a page.
   *
   * @return Returns false if at least one element is not present on the page.
   */
  public static boolean areElementsPresent(List<By> elements) {
    LOGGER.log(Level.INFO, "CALLED: areElementsPresent()");
    boolean present = true;
    for (By locator : elements) {
      if (!isElementPresent(locator)) {
        present = false;
        LOGGER.log(Level.WARNING, "{0} element is NOT present on a page", locator);
      } else {
        LOGGER.log(Level.INFO, "{0} element is present on a page", locator);
      }
    }
    return present;
  }

  /**
   * Attempts to pull locator from the element.
   *
   * @author https://stackoverflow.com/a/51080338/1318411.
   */
  protected String getLocator(WebElement webElement) {
    try {
      Object proxyOrigin = FieldUtils.readField(webElement, "h", true);
      Object locator = FieldUtils.readField(proxyOrigin, "locator", true);
      Object findBy = FieldUtils.readField(locator, "by", true);
      if (findBy != null) {
        return findBy.toString();
      }
    } catch (IllegalAccessException e) {

    }
    return StringUtils.EMPTY;
  }

  /**
   * Finds element and populates text to it.
   */
  public static void populateField(By locator, String text) {
    LOGGER.log(Level.INFO, "CALLED: populateField()");
    populateField(getDriver().findElement(locator), text);
  }

  /**
   * Finds element and populates text to it.
   */
  public static void populateField(WebElement element, String text) {
    LOGGER.log(Level.INFO, "CALLED: populateField()");
    element.clear();
    element.sendKeys(text);
  }

  public static void clearInput(By locator) {
    LOGGER.log(Level.INFO, "CALLED: clearInput()");
    getDriver().findElement(locator).clear();
  }

  public static String getInputFieldValue(By locator) {
    LOGGER.log(Level.INFO, "CALLED: getInputFieldValue()");
    return getInputFieldValue(getDriver().findElement(locator));
  }

  public static String getInputFieldValue(WebElement webElement) {
    LOGGER.log(Level.INFO, "CALLED: getInputFieldValue()");
    String value = webElement.getAttribute("value").trim();
    LOGGER.log(Level.INFO, "value: " + value);
    return value;
  }

  /**
   * Checks if checkbox is checked.
   *
   * @return Returns true if the checkbox is checked.
   */
  public static boolean isCheckboxChecked(By locator) {
    LOGGER.log(Level.INFO, "CALLED: isCheckboxChecked()");
    boolean checked = getDriver().findElement(locator).isSelected();
    LOGGER.log(Level.INFO, "Checked: " + checked);
    return checked;
  }

  /**
   * Clicks on the checkbox regardless of its state.
   */
  public static void clickCheckbox(By locator) {
    LOGGER.log(Level.INFO, "CALLED: clickCheckbox()");
    WebElement checkbox = getDriver().findElement(locator);
    checkbox.click();
  }

  /**
   * Clicks on the checkbox only if it is not selected.
   */
  public static void selectCheckbox(By locator) {
    LOGGER.log(Level.INFO, "CALLED: clickCheckbox()");
    WebElement checkbox = getDriver().findElement(locator);
    if (!checkbox.isSelected()) {
      checkbox.click();
    }
  }

  /**
   * Deselects a checkbox only if its selected.
   */
  public static void unselectCheckbox(By locator) {
    LOGGER.log(Level.INFO, "CALLED: clickCheckbox()");
    WebElement checkbox = getDriver().findElement(locator);
    if (checkbox.isSelected()) {
      checkbox.click();
    }
  }

  /**
   * Clicks an element and waits for an element to become stale.
   */
  public static void clickAndWait(By locator, Duration timeout) {
    clickAndWait(getDriver().findElement(locator), timeout);
  }

  /**
   * Clicks an element and waits for element to become stale.
   */
  public static void clickAndWait(WebElement webElement, Duration timeout) {
    LOGGER.log(Level.INFO, "called: clickAndWait()");
    webElement.click();
    WaiterUtil.waitForElementToBecomeStale(webElement, timeout);
  }

  /**
   * Attempts to click an element and if WebDriverException is thrown, clicks the element with JavaScript.
   */
  public static void specialClick(WebElement webElement) {
    LOGGER.log(Level.INFO, "CALLED: specialClick()");
    try {
      webElement.click();
      LOGGER.log(Level.INFO, "Element is clicked");
    } catch (WebDriverException e) {
      LOGGER.log(Level.WARNING, "WebDriverException is thrown. Will click using JavaScript");
      JavaScriptUtil.click(webElement);
    }
  }

  /**
   * Attempts to click an element and if WebDriverException is thrown, clicks the element with JavaScript.
   */
  public static void specialClick(By locator) {
    specialClick(getDriver().findElement(locator));
  }

  /**
   * Hover over an element using Selenium built-in Actions.
   */
  public static void mouseOver(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: hoverOverElement( {0} )", element);
    new Actions(getDriver())
        .moveToElement(element)
        .perform();
  }

  public static String getText(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: getText()");
    return element.getText();
  }

  public static String getText(By locator) {
    return getText(getDriver().findElement(locator));
  }

  /**
   * Converts List<WebElement> to List<String> where each string is a text from an element.
   */
  public static List<String> getTextList(List<WebElement> elementList) {
    LOGGER.log(Level.INFO, "CALLED: getTextList()");
    return elementList.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());
  }

  public static void selectDropdown(WebElement element, String value) {
    LOGGER.log(Level.INFO, "CALLED: selectDropdown( {0}, {1} )", new Object[]{element, value});
    try {
      Select DropDown = new Select(element);
      DropDown.selectByVisibleText(value);
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Failed to selectDropdown", ex);
    }
  }

}
