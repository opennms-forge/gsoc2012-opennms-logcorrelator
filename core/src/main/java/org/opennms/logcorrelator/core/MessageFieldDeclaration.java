package org.opennms.logcorrelator.core;

import org.apache.commons.lang.WordUtils;
import org.opennms.logcorrelator.api.Message;


/**
 * Wrapper for a field declaration.
 *
 * The wrapper holds the name and the type of a declared field. Additionally it
 * provides some helper functions for code generation.
 *
 * @param <T> the type of the field
 */
public final class MessageFieldDeclaration<T> {
  /**
   * The code generator used for declaration.
   */
  private final MessageCodeGenerator codeGenerator;

  /**
   * The name of the field.
   */
  private final String name;

  /**
   * The type of the field.
   */
  private final Class<T> type;

  /**
   * Creates a new field declaration wrapper.
   *
   * @param codeGenerator the code generator used to build the implementations
   * @param name the name of the field
   * @param type the type of the field
   */
  public MessageFieldDeclaration(final MessageCodeGenerator codeGenerator,
                                 final String name,
                                 final Class<T> type) {
    this.codeGenerator = codeGenerator;

    this.name = name;
    this.type = type;
  }

  /**
   * Returns the name of the field.
   *
   * @return the name of the field
   */
  public final String getName() {
    return this.name;
  }

  /**
   * Returns the type of the field.
   *
   * @return the type
   */
  public final Class<T> getType() {
    return this.type;
  }

  /**
   * Returns the full qualified class name of the fields type.
   *
   * @return the class name
   */
  public final String getTypeClass() {
    return this.type.getName();
  }

  /**
   * Returns the name of the read method for the field.
   *
   * @return the method name
   */
  public final String getReadMethod() {
    StringBuilder s = new StringBuilder();
    s.append("get");
    s.append(WordUtils.capitalize(this.name));

    return s.toString();
  }

  /**
   * Returns the name of the write method for the field.
   *
   * @return the method name
   */
  public final String getWriteMethod() {
    StringBuilder s = new StringBuilder();
    s.append("set");
    s.append(WordUtils.capitalize(this.name));

    return s.toString();
  }

  /**
   * Returns the name of the accessor class for the field.
   *
   * @return the class name
   */
  public final String getAccessorClass() {
    StringBuilder s = new StringBuilder();
    s.append(WordUtils.capitalize(this.name));
    s.append("MessageAccessorImpl");

    return s.toString();
  }

  /**
   * Returns the name of the message class for the field.
   *
   * @return the class name
   */
  public final String getMessageClass() {
    return Message.class.getName();
  }

  /**
   * Returns the name of the message implementation class for the field.
   *
   * @return the class name
   */
  public final String getMessageImplementationClass() {
    return this.codeGenerator.getMessageImplementationName();
  }

}
