package com.schneider.electric.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyboardUtil {

  private static final Logger LOGGER = Logger.getLogger(DriverUtil.class.getName());

  public static void pressEnterButton() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressEnterButton()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_ENTER);
  }

  public static void pressEscButton() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressEscButton()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_ESCAPE);
  }

  public static void pressDeleteButton() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressDeleteButton()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_DELETE);
  }

  public static void pressBackspaceButton() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressBackspaceButton()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_BACK_SPACE);
  }

  public static void pressF11Button() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressF11Button()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_F11);
  }

  public static void pressCtrlButton() throws AWTException {
    LOGGER.log(Level.INFO, "CALLED: pressCtrlButton()");
    Robot robot = new Robot();
    robot.keyPress(KeyEvent.VK_CONTROL);
  }
  
}
