package com.schneider.electric.util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.Point;

import com.schneider.electric.enums.MobileScreenSize;
import com.schneider.electric.enums.ScreenSize;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserWindowUtil extends SeleniumUtil {

  private static final Logger LOGGER = Logger.getLogger(BrowserWindowUtil.class.getName());

  public static String getTitle() {
    String title = getDriver().getTitle();
    LOGGER.log(Level.INFO, "Current Title: {0}", title);
    return title;
  }

  public static int getWindowCount() {
    int count = getDriver().getWindowHandles().size();
    LOGGER.log(Level.INFO, "Number of windows opened: {0}", count);
    return count;
  }

  /**
   * Closes the current window. NOTE: You will need to switch to another tab after closing the current.
   */
  public static void closeCurrentWindow() {
    getDriver().close();
    LOGGER.log(Level.INFO, "close the current window");
  }

  /**
   * Closes all browser windows.
   */
  public static void closeAllWindows() {
    getDriver().getWindowHandles()
        .forEach(
            handle -> getDriver().switchTo().window(handle)
                .close()
        );
    LOGGER.log(Level.INFO, "close the all windows");
  }

  public static String getCurrentWindowHandle() {
    String handle = getDriver().getWindowHandle();
    LOGGER.log(Level.INFO, "Current window handle: {0}", handle);
    return handle;
  }

  public static Set<String> getWindowHandles() {
    Set<String> handles = getDriver().getWindowHandles();
    LOGGER.log(Level.INFO, "Window handles: {0}", handles.toString());
    return handles;
  }

  public static boolean isWindowWithHandlePresent(String handle) {
    return getWindowHandles()
        .contains(handle);
  }

  public static boolean isWindowPresent(String title) {
    String currentWindowHandle = getCurrentWindowHandle();

    boolean found = false;
    Set<String> handles = getDriver().getWindowHandles();
    for (String handle : handles) {
      getDriver().switchTo().window(handle);
      String currentTitle = getDriver().getTitle().trim();
      if (StringUtils.equalsIgnoreCase(currentTitle, title)) {
        LOGGER.log(Level.INFO, "Found window using title {0}", title);
        found = true;
        break;
      }
    }

    if (!found) {
      LOGGER.log(Level.WARNING, "Window is not found using title {0}. Switching back to the initial window.", title);
      getDriver().switchTo().window(currentWindowHandle);
    }

    return found;
  }

  public static void maximizeCurrentWindow() {
    LOGGER.log(Level.INFO, "CALLED: maximizeCurrentWindow()");
    getDriver().manage().window().maximize();
  }

  public static void moveBrowserWindow(int x, int y) {
    LOGGER.log(Level.INFO, "CALLED: moveBrowserWindow( {0}, {1})", new Object[]{x, y});
    getDriver().manage().window()
        .setPosition(new Point(x, y));
  }

  public static void resizeBrowserWindow(int width, int height) {
    LOGGER.log(Level.INFO, "CALLED: maximizeCurrentWindow( {0}, {1})", new Object[]{width, height});
    getDriver().manage().window()
        .setSize(new Dimension(width, height));
  }

  public static void resizeBrowserWindow(ScreenSize screenSize) {
    resizeBrowserWindow(screenSize.width, screenSize.height);
  }

  public static void resizeBrowserWindow(MobileScreenSize mobileScreenSize) {
    resizeBrowserWindow(mobileScreenSize.width, mobileScreenSize.height);
  }

  public static Dimension getBrowserWindowSize() {
    LOGGER.log(Level.INFO, "CALLED: getBrowserWindowSize()");
    return getDriver().manage().window().getSize();
  }

  public static Point getBrowserWindowPosition() {
    LOGGER.log(Level.INFO, "CALLED: getBrowserWindowPosition()");
    return getDriver().manage().window().getPosition();
  }

  public static boolean switchToWindow(int index) throws NoSuchWindowException {
    LOGGER.log(Level.INFO, "CALLED: switchToWindow(\"" + index + "\")");

    Set<String> handles = getDriver().getWindowHandles();
    int windowCount = handles.size();
    LOGGER.log(Level.INFO, "Number of currently shown windows: {0}", windowCount);

    if (index > windowCount) {
      throw new NoSuchWindowException("index argument is greater than the current window count");
    }
    if (index <= 0) {
      throw new IllegalArgumentException("index argument must be a positive integer");
    }

    boolean windowFound = false;
    int i = 1;
    for (String handle : handles) {
      if (i == index) {
        getDriver().switchTo().window(handle);
        LOGGER.log(Level.INFO, "Switched to window with index : {0}", index);
        windowFound = true;
        break;
      }
      i++;
    }

    return windowFound;
  }

  public static boolean switchToWindowWhereTitleEquals(String title) {
    return switchToWindow(title, true);
  }

  public static boolean switchToWindowWhereTitleContains(String title) {
    return switchToWindow(title, false);
  }

  private static boolean switchToWindow(String title, boolean equals) {
    String currentWindowHandle = getCurrentWindowHandle();

    boolean found = false;
    Set<String> handles = getWindowHandles();
    LOGGER.log(Level.INFO, "Number of windows: {0}", handles.size());
    for (String handle : handles) {
      getDriver().switchTo().window(handle);
      String currentTitle = getDriver().getTitle().trim();
      if ((equals && StringUtils.equalsIgnoreCase(currentTitle, title))
          || (!equals && StringUtils.equalsIgnoreCase(currentTitle.toLowerCase(), title.toLowerCase()))) {
        LOGGER.log(Level.INFO, "Found window using title {0}", title);
        found = true;
        break;
      }
    }

    if (!found) {
      getDriver().switchTo().window(currentWindowHandle);
    }

    return found;
  }

}
