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
  public abstract void transmogrify(final Preprocessor preprocessor,
                                    final Message message);

}
