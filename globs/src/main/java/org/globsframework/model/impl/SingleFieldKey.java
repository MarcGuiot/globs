package org.globsframework.model.impl;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.*;
import org.globsframework.model.DefaultKey;
import org.globsframework.model.FieldValue;
import org.globsframework.model.FieldValues;
import org.globsframework.model.Key;
import org.globsframework.utils.Utils;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.MissingInfo;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class SingleFieldKey extends DefaultKey {
   private Object value;
   private Field keyField;
   private int hashCode = 0;

   public SingleFieldKey(Field field, Object value) throws MissingInfo {
      checkValue(field, value);
      this.keyField = field;
      this.keyField.checkValue(value);
      this.value = value;
   }

   static void checkValue(Field field, Object value) throws MissingInfo {
//    if (value == null) {
//      throw new MissingInfo("Field '" + field.getName() +
//                            "' missing (should not be NULL) for identifying a Glob of type: " + field.getGlobType().getName());
//    }
   }

   public SingleFieldKey(GlobType type, Object value) throws InvalidParameter {
      this(getKeyField(type), value);
   }

   private static Field getKeyField(GlobType type) throws InvalidParameter {
      Field[] keyFields = type.getKeyFields();
      if (keyFields.length != 1) {
         throw new InvalidParameter("Cannot use a single field key for type " + type + " - " +
                                    "key fields=" + Arrays.toString(keyFields));
      }
      return keyFields[0];
   }

   public GlobType getGlobType() {
      return keyField.getGlobType();
   }

   public void apply(FieldValues.Functor functor) throws Exception {
      functor.process(keyField, value);
   }

   public boolean contains(Field field) {
      return keyField.equals(field);
   }

   public void safeApply(FieldValues.Functor functor) {
      try {
         functor.process(keyField, value);
      }
      catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public int size() {
      return 1;
   }

   public byte[] get(BlobField field) {
      checkIsKeyField(field);
      return (byte[])value;
   }

   public Boolean get(BooleanField field) {
      checkIsKeyField(field);
      return (Boolean)value;
   }

   public boolean isTrue(BooleanField field) {
      return Boolean.TRUE.equals(get(field));
   }

   public Double get(DoubleField field) {
      checkIsKeyField(field);
      return (Double)value;
   }

   public Object getValue(Field field) {
      checkIsKeyField(field);
      return value;
   }

   public Integer get(IntegerField field) {
      checkIsKeyField(field);
      return (Integer)value;
   }

   public Long get(LongField field) {
      checkIsKeyField(field);
      return (Long)value;
   }

   public String get(StringField field) {
      checkIsKeyField(field);
      return (String)value;
   }

   // optimized - do not use generated code
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null) {
         return false;
      }
      if (o.getClass().equals(SingleFieldKey.class)) {
         SingleFieldKey otherSingleFieldKey = (SingleFieldKey)o;
         return otherSingleFieldKey.keyField.equals(keyField) &&
                Utils.equal(otherSingleFieldKey.value, value);
      }

      if (!DefaultKey.class.isAssignableFrom(o.getClass())) {
         return false;
      }
      Key otherKey = (Key)o;
      return keyField.getGlobType().equals(otherKey.getGlobType())
             && Utils.equal(value, otherKey.getValue(keyField));
   }

   // optimized - do not use generated code
   public int hashCode() {
      if (hashCode != 0) {
         return hashCode;
      }
      int hash = getGlobType().hashCode();
      hash = 31 * hash + (value != null ? value.hashCode() : 0);
      if (hash == 0){
         hash = 31;
      }
      this.hashCode = hash;
      return hashCode;
   }

   public FieldValue[] toArray() {
      return new FieldValue[]{
         new FieldValue(keyField, value),
      };
   }

   public String toString() {
      return getGlobType().getName() + "[" + keyField.getName() + "=" + value + "]";
   }
}
