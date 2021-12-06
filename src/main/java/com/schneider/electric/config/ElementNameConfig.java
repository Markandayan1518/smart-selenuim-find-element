package com.schneider.electric.config;

import com.schneider.electric.annotation.Name;

import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ElementNameConfig {

  public static Map<WebElement, String> map = new HashMap<>();

  public static void store(Object pageObject) {
    Field[] fields = pageObject.getClass().getDeclaredFields();
    for (Field field : fields) {
      Name annotation = field.getAnnotation(Name.class);
      if (annotation != null) {
        try {
          map.put((WebElement) field.get(pageObject), annotation.value());
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static String getName(WebElement element) {
    return map.get(element);
  }

}
