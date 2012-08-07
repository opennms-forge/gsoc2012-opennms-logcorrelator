package org.opennms.logcorrelator.correlators.drools;

import org.opennms.logcorrelator.api.Correlator;
import org.opennms.logcorrelator.api.CorrelatorFactory;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.config.CorrelatorConfiguration;


public class DroolsCorrelatorFactory implements CorrelatorFactory {
  @Override
  public Correlator create(final MessageFactory messageFactory,
                           final CorrelatorConfiguration configuration) {
    return new DroolsCorrelator(messageFactory);
  }

}
