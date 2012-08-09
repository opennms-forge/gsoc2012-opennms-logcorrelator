package org.opennms.logcorrelator.api;


/**
 * Interface for the turn-the-message-into-something-else-o-mat.
 *
 * A transmogrifier is the definition of the job a specific {@link Preprocessor}
 * does with a message.
 *
 * Remember: The transmogrifier must be turned up-side down to work
 * (http://calvinandhobbes.wikia.com/wiki/Transmogrifier)
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface Transmogrifier extends MessageDeclarationProvider, Plugin {
  /**
   * The context where the transmogrifier is running in.
   *
   * A transmogrifier uses the context as a communication channel to the outer
   * world.
   */
  public static interface Context {
    /**
     * Relays the message to the next processor in the chain.
     *
     * @param message the message to relay
     */
    public abstract void pass(final Message message);

    /**
     * Returns the ID of the context.
     *
     * @return the ID
     */
    public abstract String getId();

    /**
     * Create a message.
     *
     * @return the message
     */
    public abstract Message createMessage();

    /**
     * Creates a copy of the given message.
     *
     * @param message the message to copy
     *
     * @return the copied message
     */
    public abstract Message copyMessage(final Message message);

  }

  /**
   * Handles messages to preprocess.
   *
   * Each message processed by the preprocessor is passed to this method. The
   * message is allowed to manipulate the messages in any manner.
   *
   * To forward a message to the next processor, the implementations must call
   * {@link Preprocessor#pass(org.opennms.logcorrelator.api.Message)}.
   *
   * @param message the message to modify
   */
  public abstract void transmogrify(final Context context,
                                    final Message message);

}
