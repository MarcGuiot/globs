package org.globsframework.metamodel.annotations;

import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.BooleanField;
import org.globsframework.metamodel.fields.DoubleField;
import org.globsframework.metamodel.utils.GlobTypeLoader;
import org.globsframework.model.Glob;
import org.globsframework.model.Key;

public class DefaultBooleanAnnotationType {
    public static GlobType DESC;

    @DefaultFieldValue
    public static BooleanField DEFAULT_VALUE;

    @InitUniqueKey
    public static Key UNIQUE_KEY;

    public static Glob create(DefaultBoolean defaultDouble) {
        return DESC.instantiate().set(DEFAULT_VALUE, defaultDouble.value());
    }

    static {
        GlobTypeLoader.init(DefaultBooleanAnnotationType.class);
    }
}
