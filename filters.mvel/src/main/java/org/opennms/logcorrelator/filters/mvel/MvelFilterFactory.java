package org.opennms.logcorrelator.filters.mvel;

import org.opennms.logcorrelator.api.Filter;
import org.opennms.logcorrelator.api.FilterFactory;
import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.logcorrelator.api.TransmogrifierFactory;
import org.opennms.logcorrelator.config.xml.PreprocessorConfiguration;


public final class MvelFilterFactory implements FilterFactory {

  @Override
  public final Filter create(final String expression) {
    return new MvelFilter(expression);
  }

}
