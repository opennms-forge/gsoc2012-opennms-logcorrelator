package org.opennms.logcorrelator.correlators.nop;

import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.correlators.opennms.AbstractCorrelator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NopCorrelator extends AbstractCorrelator {
  Logger logger = LoggerFactory.getLogger(NopCorrelator.class);

  public NopCorrelator() {
  }

  @Override
  public void init() {
    logger.debug("Initialize NOP correlator");
  }

  @Override
  public void destroy() {
    logger.debug("Destroy NOP correlator");
  }

  @Override
  public void start() {
    logger.debug("Start NOP correlator");
  }

  @Override
  public void stop() {
    logger.debug("Stop NOP correlator");
  }

  @Override
  public void process(final Message message) {
    logger.debug("Forwarding message to almighty event bus: {}", message);

    this.sendEvent(message);
  }

  @Override
  public String getId() {
    return "nop";
  }

  @Override
  public void registerMessageDeclaration(MessageDeclarator declarator) {
  }

}
