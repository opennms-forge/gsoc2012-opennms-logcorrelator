package org.opennms.logcorrelator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PreprocessorConfiguration extends AbstractPluginConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(PreprocessorConfiguration.class);

  private String id;

  private FiltersConfiguration filters = new FiltersConfiguration();

  public final String getId() {
    return this.id;
  }

  public final void setId(final String id) {
    this.id = id;
  }

  public final FiltersConfiguration getFilters() {
    return this.filters;
  }

}
