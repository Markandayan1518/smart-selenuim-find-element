package com.schneider.electric.util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaiterUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(SeleniumUtil.class.getName());

  /**
   * Sets implicit wait to 0.
   * @return
   */
  public static void disableImplicitWaits() {
    LOGGER.log(Level.INFO, "CALLED: turnOffImplicitWaits()");
    getDriver().manage().timeouts()
        .implicitlyWait(0, TimeUnit.SECONDS);
  }

  /**
   * Sets implicit wait.
   * @return
   */
  public static void enableImplicitWaits(Duration duration) {
    LOGGER.log(Level.INFO, "CALLED: turnOnImplicitWaits( {0} )", duration);
    getDriver().manage().timeouts()
        .implicitlyWait(duration.getSeconds(), TimeUnit.SECONDS);
  }

  public static void waitForAlert(Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForAlert( {0} )", timeout);
    new WebDriverWait(getDriver(), timeout)
        .ignoring(NoAlertPresentException.class)
        .until(ExpectedConditions.alertIsPresent());
  }

  public static boolean waitForAlertAndAccept(Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForAlertAndAccept( {0} )", timeout);
    try {
      WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
      Alert alert = wait.until(ExpectedConditions.alertIsPresent());
      LOGGER.log(Level.INFO, "Alert text: {0}", alert.getText());
      alert.accept();
      LOGGER.log(Level.INFO, "Alert is accepted");
      return true;
    } catch (TimeoutException e) {
      LOGGER.log(Level.WARNING, "Alert was NOT displayed within specified timeout");
      return false;
    }
  }

  public static boolean waitForDomToComplete(Duration timeout, Duration sleep) {
    return JavaScriptUtil.waitForDomToComplete(timeout, sleep);
  }

  public static boolean waitForDomToComplete(Duration timeout) {
    return waitForDomToComplete(timeout, SeleniumUtil.defaultSleep);
  }

  /**
   * Uses below JavaScript to determine if Angular finished executing:
   * <code>
   *      return (window.angular !== undefined)
   * 		&& (angular.element(document.getElementById('ng-app')).injector() !== undefined)
   * 		&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0)
   * </code>
   * Current method will use default SeleniumUtil.defaultTimeoutInSeconds timeout and SeleniumUtil.defaultPollingPeriodInMilliseconds polling frequency.
   * @return
   */
  public static boolean waitForAngularToComplete() {
    return waitForAngularToComplete(SeleniumUtil.defaultTimeout, SeleniumUtil.defaultSleep);
  }

  /**
   * Uses below JavaScript to determine if Angular finished executing:
   * <code>
   *      return (window.angular !== undefined)
   * 		&& (angular.element(document.getElementById('ng-app')).injector() !== undefined)
   * 		&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0)
   * </code>
   * @param timeout
   * @param polling
   * @return
   */
  public static boolean waitForAngularToComplete(Duration timeout, Duration polling) {
    LOGGER.log(Level.INFO, "CALLED: waitForAngularToComplete(" + timeout + ", " + polling + ")");
    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
        .withTimeout(timeout)
        .pollingEvery(polling)
        .withMessage("Angular requests are still pending");
    return wait.until((ExpectedCondition<Boolean>) arg0 -> Boolean.valueOf(((JavascriptExecutor) arg0)
        .executeScript("return (window.angular !== undefined) "
            + "	&& (angular.element(document.getElementById('ng-app')).injector() !== undefined) "
            + "	&& (angular.element(document.getElementById('ng-app')).injector().get('$http').pendingRequests.length === 0);")
        .toString()));
  }

  /**
   * Waits for element to be present on page and to be clickable within the specified timeOut.
   * @param locator
   * @param timeout
   * @return
   */
  public static boolean waitForElement(By locator, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElement()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
    try{
      wait.until(ExpectedConditions.presenceOfElementLocated(locator));
      wait.until(ExpectedConditions.elementToBeClickable(locator));
      LOGGER.log(Level.INFO, "Element is present");
      return true;
    }
    catch(TimeoutException ex){
      LOGGER.log(Level.WARNING, "Timeout is reached. Element is NOT present");
      return false;
    }
  }

  public static boolean waitForElementToBePresent(By locator, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToBecomePresent()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
    try{
      wait.until(ExpectedConditions.presenceOfElementLocated(locator));
      LOGGER.log(Level.INFO, "Element is present");
      return true;
    }
    catch(TimeoutException te){
      LOGGER.log(Level.WARNING, "Timeout is reached. Element is NOT present");
      return false;
    }
  }

  public static boolean waitForElementToBeClickable(By locator, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToBeClickable()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
    try{
      wait.until(ExpectedConditions.elementToBeClickable(locator));
      LOGGER.log(Level.INFO, "Element is clickable");
      return true;
    }
    catch(TimeoutException te){
      LOGGER.log(Level.WARNING, "Timeout is reached. Element is NOT clickable");
      return false;
    }
  }

	/*
	public static boolean waitForElementToBecomeVisible(WebElement webElement, Duration timeout) {
		LOGGER.log(Level.INFO, "CALLED: waitForElementToBecomeVisible()");
		WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(webElement));
		return webElement.isEnabled();
	}
	 */

  public static boolean waitForElementToBecomeVisible(WebElement element, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToBecomeVisible()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
    try{
      wait.until(ExpectedConditions.visibilityOf(element));
      LOGGER.log(Level.INFO, "Element is visible");
      return true;
    }
    catch(TimeoutException te){
      LOGGER.log(Level.WARNING, "Timeout is reached. Element is NOT visible");
      return false;
    }
  }

  public static boolean waitForElementToBecomeVisible(By locator, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToBecomeVisible()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
    try{
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      LOGGER.log(Level.INFO, "Element is visible");
      return true;
    }
    catch(TimeoutException te){
      LOGGER.log(Level.WARNING, "Timeout is reached. Element is NOT visible");
      return false;
    }
  }

  public static boolean waitForElementAttributeToContainValue(WebElement webElement, String attribute, String value, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementAttributeToContainValue()");
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout);
    wait.until(ExpectedConditions.attributeContains(webElement, attribute, value));
    return webElement.getAttribute(attribute).contains(value);
  }

  public static boolean waitForElementToBecomeStale(WebElement webElement, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToBecomeStale()");
    WebDriverWait wait =  new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
    return wait.until(ExpectedConditions.stalenessOf(webElement));
  }

  /**
   * Waits for element to disappear from page withing specified timeout.
   * @param locator
   * @param timeout
   * @return
   */
  public static boolean waitForElementToDisappear(By locator, Duration timeout) {
    LOGGER.log(Level.INFO, "CALLED: waitForElementToDisappear()");
    WebDriverWait wait =  new WebDriverWait(getDriver(), timeout, SeleniumUtil.defaultSleep);
    try{
      wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
      return true;
    }
    catch(TimeoutException te){
      return false;
    }
  }
}
