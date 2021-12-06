package com.schneider.electric.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaScriptUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(JavaScriptUtil.class.getName());

  public static void openNewTab() {
    LOGGER.log(Level.INFO, "CALLED: openNewTab()");
    ((JavascriptExecutor) getDriver()).executeScript("window.open()");
    // ((JavascriptExecutor) getDriver()).executeScript("window.open('about:blank', '_blank')");
  }

  public static void openNewTabAndSwitch() {
    LOGGER.log(Level.INFO, "CALLED: openNewTabAndSwitch()");
    int windowCount = BrowserWindowUtil.getWindowCount();
    openNewTab();
    BrowserWindowUtil.switchToWindow(windowCount + 1);
  }

  public static void openNewTab(String url) {
    LOGGER.log(Level.INFO, "CALLED: openNewTab( {0} )", url);
    ((JavascriptExecutor) getDriver()).executeScript("window.open('" + url + "')");
    // ((JavascriptExecutor) getDriver()).executeScript("window.open('" + url + "', '_blank')");
  }

  public static void openNewTabAndSwitch(String url) {
    LOGGER.log(Level.INFO, "CALLED: openNewTabAndSwitch( {0} )", url);
    int windowCount = BrowserWindowUtil.getWindowCount();
    openNewTab(url);
    BrowserWindowUtil.switchToWindow(windowCount + 1);
  }

  public static void populateField(WebElement element, String text) {
    LOGGER.log(Level.INFO, "CALLED: populateField( {0} )", text);
    ((JavascriptExecutor) getDriver()).executeScript(
        "arguments[0].setAttribute('value','" + text + "')", element
    );
  }

  public static void populateField(String elementId, String text) {
    LOGGER.log(Level.INFO, "CALLED: populateField( {0}, {1})", new Object[]{elementId, text});
    ((JavascriptExecutor) getDriver())
        .executeScript("document.getElementById('" + elementId + "').value='" + text + "';");
  }

  public static void click(By locator) {
    click(getDriver().findElement(locator));
  }

  /**
   * Uses below code to hover over an element.
   * <code>
   * if(document.createEvent){ var evObj = document.createEvent('MouseEvents'); evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj); } else if(document.createEventObject) { arguments[0].fireEvent('onclick'); }
   * </code>
   */
  public static void click(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: click()");
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    String script = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
        + "evObj.initEvent('click', true, false); arguments[0].dispatchEvent(evObj);}"
        + "else if(document.createEventObject) { arguments[0].fireEvent('onclick');}";
    executor.executeScript(script, element);
  }

  public static void hoverOverElement(By locator) {
    hoverOverElement(getDriver().findElement(locator));
  }

  /**
   * Uses below code to hover over an element.
   * <code>
   * if(document.createEvent){ var evObj = document.createEvent('MouseEvents'); evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj); } else if(document.createEventObject) { arguments[0].fireEvent('onmouseover'); }
   * </code>
   */
  public static void hoverOverElement(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: hoverOverElement()");
    JavascriptExecutor je = (JavascriptExecutor) getDriver();
    String script = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
        + "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);}"
        + "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
    je.executeScript(script, element);
  }

  public static void scrollElementIntoView(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: scrollElementIntoView()");
    ((JavascriptExecutor) getDriver())
        .executeScript("arguments[0].scrollIntoView(true);", element);
  }

  /**
   * Uses the below code to scroll down the page:
   * <code>
   * window.scrollBy(0, 500);
   * </code>
   *
   * @param scrollTo defines how many pixels the page will be scrolled down.
   */
  public static void scrollDown(int scrollTo) {
    LOGGER.log(Level.INFO, "CALLED: scrollDown()");
    ((JavascriptExecutor) getDriver())
        .executeScript("window.scrollBy(0, " + scrollTo + ")");
  }

  /**
   * Using JavaScript check if an image is present on the page. Taken from https://stackoverflow.com/questions/23932402/can-anyone-help-me-explaining-javascript-code-for-image-validation-using-seleniu
   */
  public static Boolean isImagePresent(WebElement image) {
    LOGGER.log(Level.INFO, "CALLED: isImagePresent()");
    return (Boolean) ((JavascriptExecutor) getDriver())
        .executeScript("return arguments[0].complete " +
            "&& typeof arguments[0].naturalWidth != \"undefined\" " +
            "&& arguments[0].naturalWidth > 0", image);
  }

  /**
   * Uses below code to show an alert.
   * <code>
   * alert('text');
   * </code>
   */
  public static void initAlert(String text) {
    LOGGER.log(Level.INFO, "CALLED: initAlert()");
    ((JavascriptExecutor) getDriver())
        .executeScript("alert('" + text + "');");
  }

  /**
   * Uses below code to refresh the browser window.
   * <code>
   * history.go(0);
   * </code>
   */
  public static void refreshBrowserWindow() {
    LOGGER.log(Level.INFO, "CALLED: refreshBrowserWindow()");
    ((JavascriptExecutor) getDriver())
        .executeScript("history.go(0)");
  }

  /**
   * Waits for DOM to have status complete. Uses JavaScript document.readyState to get the DOM status.
   */
  public static boolean waitForDomToComplete(Duration timeout, Duration sleep) {
    LOGGER.log(Level.INFO, "CALLED: waitForDomToComplete( {0}, {1} )", new Object[]{timeout, sleep});
    WebDriverWait wait = new WebDriverWait(getDriver(), timeout, sleep);
    return wait.until((ExpectedCondition<Boolean>) driver -> {
      String readyState = (String) ((JavascriptExecutor) getDriver())
          .executeScript("return document.readyState;");
      LOGGER.log(Level.INFO, "readyState: {0}", readyState);
      return readyState.equals("complete");
    });
  }

  public static void highlight(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: highlight( {0} )", element);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      LOGGER.log(Level.SEVERE, "Failed to highlight the element", ex);
    }
    executor.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
  }

  public static void scrollToBottom(int time) {
    LOGGER.log(Level.INFO, "CALLED: scrollToBottom( {0} )", time);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    for (int i = 0; i < time; i++) {
      executor.executeScript("window.scrollBy(0,250)");
    }
  }

  public static void scrollToUp(int time) {
    LOGGER.log(Level.INFO, "CALLED: scrollToUp( {0} )", time);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    for (int i = 0; i < time; i++) {
      executor.executeScript("window.scrollBy(0,-250)");
    }
  }

  public static void scrollUpToElementIsVisible(WebElement element) {
    LOGGER.log(Level.INFO, "CALLED: scrollUpToElementIsVisible( {0} )", element);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
    executor.executeScript("arguments[0].scrollIntoView(true);", element);
  }

}