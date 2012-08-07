package org.opennms.logcorrelator.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FiltersConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(FiltersConfiguration.class);

  private String language;
  
  private List<String> filters = new ArrayList<String>();

  public void setLanguage(final String language) {
    this.language = language;
  }

  public final String getLanguage() {
    return this.language;
  }

  public final void addFilter(final String filter) {
    this.filters.add(filter);
  }

  public final List<String> getFilters() {
    return this.filters;
  }

}
