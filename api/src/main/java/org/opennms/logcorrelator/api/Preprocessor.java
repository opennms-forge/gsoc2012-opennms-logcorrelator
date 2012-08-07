package org.opennms.logcorrelator.api;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Base class for all preprocessors.
 *
 * Preprocessors are able to process a message before the message is correlated.
 * Changing a message can include modification of fields, adding or removing
 * fields, creating new messages or dropping the message.
 *
 * The work of the preprocessor is done by the {@link Transmogrifier} instance,
 * which uses {@link #pass(org.opennms.logcorrelator.api.Message)} to forward
 * messages to the next processor in the processing chain.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public class Preprocessor implements PipelineProcessor, MessageDeclarationProvider {
  private final Logger logger = LoggerFactory.getLogger(Preprocessor.class);

  private final String id;

  private Set<Filter> filters;

  private Transmogrifier transmogrifier;

  /**
   * Next processor in the chain.
   */
  private Processor nextProcessor = null;

  public Preprocessor(final String id,
                      final Set<Filter> filters,
                      final Transmogrifier transmogrifier) {
    this.id = id;
    this.filters = filters;
    this.transmogrifier = transmogrifier;
  }

  @Override
  public final void process(final Message message) {
    logger.debug("Preprocessing message: {}", message);

    // Check all filters against the values of the message
    // the first miss will pass the message without processing

    for (final Filter filter : this.filters) {

      logger.debug("Matching {} against {}",
                   message,
                   filter);

      if (!filter.match(message)) {
        logger.debug("Filter does not match - passing message to next processor");

        this.pass(message);
        return;
      }

      logger.debug("Filter matched");
    }

    logger.debug("All filters matched for {} - preprocessing message.", this.id);

    // Transmogrify the message
    this.transmogrifier.transmogrify(this,
                                     message);
  }

  @Override
  public final Processor getNextProcessor() {
    return this.nextProcessor;
  }

  @Override
  public final void setNextProcessor(final Processor nextProcessor) {
    this.nextProcessor = nextProcessor;
  }

  /**
   * Relays the message to the next processor in the chain.
   *
   * @param message the message to relay
   */
  protected final void pass(final Message message) {
    assert this.nextProcessor != null;

    logger.debug("Passing message to processor {}", this.getId(), this.nextProcessor.getId());
    this.nextProcessor.process(message);
  }

  @Override
  public final String getId() {
    return this.id;
  }

  public final Transmogrifier getTransmogrifier() {
    return this.transmogrifier;
  }

  @Override
  public void registerMessageDeclaration(MessageDeclarator declarator) {
    this.transmogrifier.registerMessageDeclaration(declarator);
  }

}
