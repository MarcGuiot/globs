package org.globsframework.streams.accessors.utils;

import org.globsframework.streams.accessors.IntegerAccessor;

public class ValueIntegerAccessor implements IntegerAccessor {
  private Integer value;

  public ValueIntegerAccessor(Integer value) {
    this.value = value;
  }

  public ValueIntegerAccessor() {
  }

  public Integer getInteger() {
    return value;
  }

  public int getValue() {
    return value;
  }

  public Object getObjectValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}
