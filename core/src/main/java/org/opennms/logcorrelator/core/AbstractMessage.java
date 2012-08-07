package org.opennms.logcorrelator.core;

import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;


/**
 * Abstract implementation of the message interface.
 * 
 * The implementation of the generic getter and setter forwards the field
 * access to the {@link MessageAccessor} implementation.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public abstract class AbstractMessage implements Message {
  private static final long serialVersionUID = -3147603979585570555L;

  @Override
  public final <T> T get(final MessageAccessor<T> accessor) {
    return accessor.get(this);
  }

  @Override
  public final <T> void set(final MessageAccessor<T> accessor,
                            final T value) {
    accessor.set(this,
                 value);
  }

}
