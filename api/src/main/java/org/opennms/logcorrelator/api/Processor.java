package org.opennms.logcorrelator.api;

public interface Processor {

  /**
   * Returns the ID of the processor.
   */
  public abstract String getId();
  
  /**
   * Process the given message.
   *
   * @param message the message to process
   */
  public abstract void process(final Message message);

}
