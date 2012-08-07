package org.opennms.logcorrelator.api;

import org.opennms.logcorrelator.config.PreprocessorConfiguration;


/**
 * Factory for a {@link Transmogrifier}.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface TransmogrifierFactory {
  public abstract Transmogrifier create(final PreprocessorConfiguration configuration);

}
