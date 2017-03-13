package org.globsframework.metamodel.links;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.Link;
import org.globsframework.metamodel.utils.AnnotatedPropertyHolder;
import org.globsframework.metamodel.utils.Annotations;
import org.globsframework.metamodel.utils.MutableGlobType;
import org.globsframework.utils.exceptions.InvalidParameter;
import org.globsframework.utils.exceptions.UnexpectedApplicationState;

import java.util.HashMap;
import java.util.Map;

public class DefaultLink extends AnnotatedPropertyHolder<Link> implements Link {
  public static final String DEFAULT_MODEL_NAME = "DEFAULT";
  private String name;
  private MutableGlobType sourceType;
  private GlobType targetType;
  private Map<Field, Field> fieldsMap = new HashMap<Field, Field>();
  private boolean initialized = false;
  private boolean required;

  public DefaultLink(MutableGlobType type, String name, boolean required,
                     Annotations annotations) {
    super(annotations);
    this.name = name;
    this.sourceType = type;
    this.required = required;
  }

  public void addFieldMapping(Field sourceField, Field targetField) {
    if (sourceField == null) {
      throw new IllegalArgumentException("Source field for link " + name + " must not be null (circular reference " +
                                         "in static initialisation)");
    }
    if (targetField == null) {
      throw new InvalidParameter("Target field for link " + name + " must not be null (circular reference " +
                                 "in static initialisation)");
    }
    if (!sourceField.getGlobType().equals(sourceType)) {
      throw new InvalidParameter("Source field '" + sourceField + "' is not a field of type " +
                                 sourceType);
    }

    GlobType targetFieldType = targetField.getGlobType();
    if (targetType == null) {
      targetType = targetFieldType;
    }
    else if (!targetType.equals(targetFieldType)) {
      throw new InvalidParameter(
        "Two different target types found for link '" + name + "' of type '" + sourceType.getName() +
        "' (" + targetType.getName() + " and " + targetFieldType.getName() + ")");
    }
    fieldsMap.put(sourceField, targetField);

    if (!initialized) {
      sourceType.addLink(this);
      initialized = true;
    }
  }

  public void apply(FieldMappingFunctor functor) {
    if (fieldsMap.isEmpty()) {
      throw new UnexpectedApplicationState("At least one source must be registered for link: " + name);
    }
    for (Map.Entry<Field, Field> entry : fieldsMap.entrySet()) {
      functor.process(entry.getKey(), entry.getValue());
    }
  }

    public GlobType getSourceType() {
    return sourceType;
  }

  public GlobType getTargetType() {
    return targetType;
  }

    public String getLinkModelName() {
        return DEFAULT_MODEL_NAME;
    }

    public String getName() {
    return name;
  }

  public String getFullName() {
    return sourceType.getName() + "." + name;
  }

  public boolean isRequired() {
    return required;
  }

  public String toString() {
    return toString(name, getSourceType(), getTargetType());
  }

  public static String toString(String name, GlobType sourceType, GlobType targetType) {
    return name + "[" + sourceType.getName() + " => " +
           targetType.getName() + "]";
  }
}
