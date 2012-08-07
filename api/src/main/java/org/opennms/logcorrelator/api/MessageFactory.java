package org.opennms.logcorrelator.api;

/**
 * Factory for creating messages.
 * 
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface MessageFactory {

  /**
   * Returns the {@link Class<Message>} implementing the {@link Message}
   * interface.
   * 
   * @return the class
   */
  public abstract Class<Message> getMessageClass();
  
  /**
   * Create a message.
   * 
   * @return the message
   */
  public abstract Message createMessage();
}
