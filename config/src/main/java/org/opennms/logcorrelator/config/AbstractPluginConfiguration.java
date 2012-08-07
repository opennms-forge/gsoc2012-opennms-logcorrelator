package org.opennms.logcorrelator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractPluginConfiguration extends AbstractPropertyConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(AbstractPluginConfiguration.class);

  private String type;

  public final String getType() {
    return this.type;
  }

  public final void setType(final String type) {
    this.type = type;
  }

}
