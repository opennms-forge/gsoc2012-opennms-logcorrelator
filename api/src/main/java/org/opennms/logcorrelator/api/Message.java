package org.opennms.logcorrelator.api;

import java.io.Serializable;


/**
 * A generic message.
 *
 * The message is the core element passed through the message processing chain.
 *
 * The message consists of a list of fields forming the content of the message,
 * To access a field to the message a {@link MessageAccessor} is required.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 *
 * @see MessageAccessor
 */
public interface Message extends Serializable {
  public abstract <T> T get(final MessageAccessor<T> accessor);

  public abstract <T> void set(final MessageAccessor<T> accessor, final T value);
}
