package org.globsframework.metamodel;

import org.globsframework.model.*;
import org.globsframework.utils.exceptions.ItemNotFound;
import org.globsframework.metamodel.fields.*;

import java.util.Date;

public abstract class AbstractOptGlob extends Key implements Glob, MutableGlob {
  public abstract GlobType getType();

  public Key getKey() {
    return this;
  }

  public Key getTargetKey(Link link) {
    return null;
  }

  public boolean matches(FieldValues values) {
    return false;
  }

  public boolean matches(FieldValue... values) {
    return false;
  }

  public FieldValues getValues() {
    return null;
  }

  public boolean exists() {
    return false;
  }

  public Glob duplicate() {
    return null;
  }

  public abstract Object getValue(Field field) throws ItemNotFound;

  public Double get(DoubleField field) throws ItemNotFound {
    return (Double)getValue(field);
  }

  public Double get(DoubleField field, double valueIfNull) throws ItemNotFound {
    return (Double)getValue(field);
  }

  public Date get(DateField field) throws ItemNotFound {
    return (Date)getValue(field);
  }

  public Date get(TimeStampField field) throws ItemNotFound {
    return (Date)getValue(field);
  }


  public int get(IntegerField field, int valueIfNull) throws ItemNotFound {
    Object value = getValue(field);
    return value == null ? valueIfNull : ((Integer)value);
  }

  public Integer get(IntegerField field) throws ItemNotFound {
    return (Integer)getValue(field);
  }

  public Integer get(LinkField field) throws ItemNotFound {
    return (Integer)getValue(field);
  }

  public String get(StringField field) throws ItemNotFound {
    return (String)getValue(field);
  }

  public Boolean get(BooleanField field) throws ItemNotFound {
    return (Boolean)getValue(field);
  }

  public Boolean get(BooleanField field, boolean defaultIfNull) {
    return (Boolean)getValue(field);
  }

  public boolean isTrue(BooleanField field) throws ItemNotFound {
    return get(field);
  }

  public Long get(LongField field) throws ItemNotFound {
    return (Long)getValue(field);
  }

  public byte[] get(BlobField field) throws ItemNotFound {
    return (byte[])getValue(field);
  }

  public boolean contains(Field field) {
    return field.getGlobType() == OptGlob.type;
  }

  public int size() {
    return getGlobType().getFieldCount();
  }

  public void apply(Functor functor) throws Exception {
    for (Field field : OptGlob.type.getFields()) {
      functor.process(field, getValue(field));
    }
  }

  public void safeApply(Functor functor) {
    try {
      for (Field field : OptGlob.type.getFields()) {
        functor.process(field, getValue(field));
      }
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public FieldValue[] toArray() {
    return new FieldValue[0];
  }

  public void set(IntegerField field, Integer value) {
    setObject(field, value);
  }

  public void set(DoubleField field, Double value) {
    setObject(field, value);
  }

  public void set(StringField field, String value) {
    setObject(field, value);
  }

  public void set(DateField field, Date value) {
  }

  public void set(BooleanField field, Boolean value) {
  }

  public void set(BlobField field, byte[] value) {
  }

  public abstract Object setObject(Field field, Object value);

  public void setValues(FieldValues values) {
  }

  public GlobType getGlobType() {
    return getType();
  }
}
