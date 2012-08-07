package org.opennms.logcorrelator.api;


public interface MessageDeclarator {
  public <T> MessageAccessor<T> registerField(final String fieldName,
                                              final Class<T> fieldType);

}
