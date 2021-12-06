package com.schneider.electric.annotation;

import com.schneider.electric.config.ElementConfig;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.AbstractFindByBuilder;

import java.lang.reflect.Field;

public class FindByConfigBuilder extends AbstractFindByBuilder {

  @Override
  public By buildIt(Object annotation, Field field) {
    return findByFormatBuilder((FindByConfig) annotation);
  }

  protected By findByFormatBuilder(FindByConfig findByProperty) {
    String property = findByProperty.value();
    if (StringUtils.isEmpty(property)) {
      throw new IllegalArgumentException("Please specify the property key");
    }

    return ElementConfig.getBy(property);
  }

}
