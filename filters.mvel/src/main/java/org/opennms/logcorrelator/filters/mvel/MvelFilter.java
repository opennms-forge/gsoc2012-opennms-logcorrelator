package org.opennms.logcorrelator.filters.mvel;

import java.io.Serializable;
import org.mvel.MVEL;
import org.opennms.logcorrelator.api.Filter;
import org.opennms.logcorrelator.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class MvelFilter implements Filter {
  private final static Logger logger = LoggerFactory.getLogger(MvelFilter.class);

  private final Serializable compiled;

  
  public MvelFilter(final String expression) {
    this.compiled = MVEL.compileExpression(expression);
  }

  @Override
  public final boolean match(final Message message) {
    return (Boolean) MVEL.executeExpression(this.compiled, message);
  }

}
