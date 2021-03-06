package org.globsframework.metamodel.fields;

import java.util.Date;

public interface FieldValueVisitor {
  void visitInteger(IntegerField field, Integer value) throws Exception;

  void visitDouble(DoubleField field, Double value) throws Exception;

  void visitString(StringField field, String value) throws Exception;

  void visitDate(DateField field, Date value) throws Exception;

  void visitBoolean(BooleanField field, Boolean value) throws Exception;

  void visitTimeStamp(TimeStampField field, Date value) throws Exception;

  void visitBlob(BlobField field, byte[] value) throws Exception;

  void visitLong(LongField field, Long value) throws Exception;

  void visitLink(LinkField field, Integer value) throws Exception;
}
