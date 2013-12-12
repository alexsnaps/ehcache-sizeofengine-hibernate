package net.sf.ehcache.sizeofengine.hibernate;

import net.sf.ehcache.sizeofengine.Filter;
import net.sf.ehcache.sizeofengine.FilterConfigurator;
import org.hibernate.Session;
import org.hibernate.cache.spi.QueryKey;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.proxy.AbstractLazyInitializer;
import org.hibernate.proxy.pojo.BasicLazyInitializer;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HibernateFilterConfigurator implements FilterConfigurator {

  public static final Set<Field> ignoredFields;

  static {
    ignoredFields = Collections.unmodifiableSet(getAllFields());
  }

  @Override
  public void configure(final Filter filter) {
    for (Field field : ignoredFields) {
      filter.ignoreField(field);
    }
    filter.ignoreInstancesOf(Session.class, false);
    filter.ignoreInstancesOf(SessionImplementor.class, false);
  }

  private static Set<Field> getAllFields() {
    Set<Field> fields = new HashSet<Field>();
    fields.add(getField(QueryKey.class, "positionalParameterTypes"));
    fields.add(getField(AbstractLazyInitializer.class, "entityName"));
    fields.add(getField(BasicLazyInitializer.class, "persistentClass"));
    fields.add(getField(BasicLazyInitializer.class, "getIdentifierMethod"));
    fields.add(getField(BasicLazyInitializer.class, "setIdentifierMethod"));
    fields.add(getField(BasicLazyInitializer.class, "componentIdType"));
    try {
      fields.add(getField(JavassistLazyInitializer.class, "interfaces"));
    } catch (NoClassDefFoundError e) {
      // ignore, no javassist around...
    }
    fields.add(getField(TypedValue.class, "type"));
    return fields;
  }

  private static Field getField(final Class<?> aClass, final String fieldName) {
    final Field field;
    try {
      field = aClass.getDeclaredField(fieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException("Couldn't retrieve field '" + fieldName + "' from class " + aClass.getName(), e);
    }
    return field;
  }
}
