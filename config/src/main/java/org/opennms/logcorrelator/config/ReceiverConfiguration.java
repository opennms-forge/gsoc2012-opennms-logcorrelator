package org.opennms.logcorrelator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReceiverConfiguration extends AbstractPluginConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(ReceiverConfiguration.class);
  
  private String id;

  public final String getId() {
    return this.id;
  }

  public final void setId(final String id) {
    this.id = id;
  }
}
