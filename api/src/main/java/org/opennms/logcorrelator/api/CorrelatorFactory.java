package org.opennms.logcorrelator.api;

import org.opennms.logcorrelator.config.CorrelatorConfiguration;


/**
 * Factory for a {@link Correlator}.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface CorrelatorFactory {
  /**
   * Creates a correlator.
   *
   * @param properties the properties for the correlator implementation
   *
   * @return the correlator
   */
  public abstract Correlator create(final MessageFactory messageFactory,
                                    final CorrelatorConfiguration configuration);

}
