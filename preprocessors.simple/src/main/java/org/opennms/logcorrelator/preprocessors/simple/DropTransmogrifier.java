package org.opennms.logcorrelator.preprocessors.simple;

import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.Transmogrifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A preprocessor which drops all messages.
 *
 * This preprocessors imply drops all messages. This seems to be rather useless
 * - but wait:
 *
 *
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public class DropTransmogrifier implements Transmogrifier {
  private final Logger logger = LoggerFactory.getLogger(DropTransmogrifier.class);

  public DropTransmogrifier() {
  }

  @Override
  public void transmogrify(final Transmogrifier.Context context,
                           final Message message) {
    
    // Do nothing to drop the message - easy ;)
    logger.debug("Message {} dropped by {}.",
                 message,
                 context.getId());
  }

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
  }

  @Override
  public void init() {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void start() {
  }

  @Override
  public void stop() {
  }

}
