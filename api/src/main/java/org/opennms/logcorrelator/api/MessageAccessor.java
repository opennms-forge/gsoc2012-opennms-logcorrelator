package org.opennms.logcorrelator.api;


/**
 * Access interface for message fields.
 *
 * The message accessor allow unified access to {@link Message} fields.
 * Accessors can be acquired by declaring fields using a
 * {@link MessageDeclarationProvider}.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 *
 * @param <T> the type of the field to access
 */
public interface MessageAccessor<T> {
  /**
   * Returns the value of the field defined by the accessor from the given
   * message.
   *
   * @param message the message to get the value from
   *
   * @return the value of the field or {@code null} if no such field exists
   */
  public abstract T get(final Message message);

  /**
   * Sets the value of the field defined by the accessor on the given message.
   *
   * @param message the message to get the value from
   * @param value the value to set
   */
  public abstract void set(final Message message,
                           final T value);

  /**
   * Returns the name of the field.
   *
   * @return the name of the field
   */
  public abstract String getFieldName();

  /**
   * Returns the type of the field.
   *
   * @return the type of the field
   */
  public abstract Class<T> getFieldType();

}
