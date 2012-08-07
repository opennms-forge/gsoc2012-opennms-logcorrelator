package org.opennms.logcorrelator.config;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Configuration {
  private final static Logger logger = LoggerFactory.getLogger(Configuration.class);

  private List<ReceiverConfiguration> receivers = new ArrayList<ReceiverConfiguration>();

  public final void addReceiver(final ReceiverConfiguration receiver) {
    this.receivers.add(receiver);
  }

  public final List<ReceiverConfiguration> getReceivers() {
    return this.receivers;
  }

  private List<PreprocessorConfiguration> preprocessors = new ArrayList<PreprocessorConfiguration>();

  public final void addPreprocessor(final PreprocessorConfiguration preprocessor) {
    this.preprocessors.add(preprocessor);
  }

  public final List<PreprocessorConfiguration> getPreprocessors() {
    return this.preprocessors;
  }

  private CorrelatorConfiguration correlator = null;

  public void setCorrelator(final CorrelatorConfiguration correlator) {
    this.correlator = correlator;
  }

  public final CorrelatorConfiguration getCorrelator() {
    return this.correlator;
  }

}
