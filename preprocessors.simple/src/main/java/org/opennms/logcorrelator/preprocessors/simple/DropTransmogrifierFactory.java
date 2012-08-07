package org.opennms.logcorrelator.preprocessors.simple;

import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.logcorrelator.api.TransmogrifierFactory;
import org.opennms.logcorrelator.config.PreprocessorConfiguration;


public class DropTransmogrifierFactory implements TransmogrifierFactory {
  @Override
  public Transmogrifier create(final PreprocessorConfiguration configuration) {
    return new DropTransmogrifier();
  }

}
