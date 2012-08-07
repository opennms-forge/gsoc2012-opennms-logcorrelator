package org.opennms.logcorrelator.config;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractPropertyConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(AbstractPropertyConfiguration.class);

  private Properties properties = new Properties();

  public final Properties getProperties() {
    return this.properties;
  }

  public final void setProperty(final String key,
                                final String value) {
    this.properties.setProperty(key,
                                value);
  }

}
