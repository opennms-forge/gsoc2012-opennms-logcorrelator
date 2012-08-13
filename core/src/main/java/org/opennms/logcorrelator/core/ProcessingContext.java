package org.opennms.logcorrelator.core;

import java.util.ArrayList;
import java.util.List;
import org.opennms.logcorrelator.api.*;
import org.opennms.logcorrelator.config.xml.Configuration;
import org.opennms.logcorrelator.config.xml.PreprocessorConfiguration;
import org.opennms.logcorrelator.config.xml.ReceiverConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProcessingContext {
  private final static Logger logger = LoggerFactory.getLogger(ProcessingContext.class);

  public static ProcessingContext create(final Configuration config) throws Exception {

    // Create message pipeline implementation
    final PipelineImpl pipeline = new PipelineImpl();

    // Create message factory implementation
    final MessageFactoryImpl messageFactory = new MessageFactoryImpl();

    // Create receiver instances
    final List<Receiver> receivers = new ArrayList<Receiver>();
    for (ReceiverConfiguration receiverConfig : config.getReceivers()) {
      final Receiver receiver = PluginFactory.createReceiver(pipeline,
                                                             messageFactory,
                                                             receiverConfig);

      receivers.add(receiver);

      messageFactory.register(receiver);
    }

    // Create preprocessor instances
    final List<Preprocessor> preprocessors = new ArrayList<Preprocessor>();
    for (PreprocessorConfiguration preprocessorConfig : config.getPreprocessors()) {
      final Preprocessor preprocessor = PluginFactory.createPreprocessor(messageFactory,
                                                                         preprocessorConfig);

      preprocessors.add(preprocessor);

      pipeline.append(preprocessor);
      messageFactory.register(preprocessor);
    }

    // Create correlator instance
    final Correlator correlator = PluginFactory.createCorrelator(messageFactory,
                                                                 config.getCorrelator());
    pipeline.append(correlator);
    messageFactory.register(correlator);

    // Produce message class implementation
    messageFactory.produce();

    return new ProcessingContext(receivers,
                                 preprocessors,
                                 correlator);
  }

  private final List<Receiver> receivers;

  private final List<Preprocessor> preprocessors;

  private final Correlator correlator;

  private ProcessingContext(final List<Receiver> receivers,
                            final List<Preprocessor> preprocessors,
                            final Correlator correlator) {
    this.receivers = receivers;
    this.preprocessors = preprocessors;
    this.correlator = correlator;
  }

  public final void init() {
    this.correlator.init();

    for (final Preprocessor preprocessor : this.preprocessors) {
      preprocessor.getTransmogrifier().init();
    }

    for (final Receiver receiver : this.receivers) {
      receiver.init();
    }
  }

  public final void destroy() {
    for (final Receiver receiver : this.receivers) {
      receiver.destroy();
    }

    for (final Preprocessor preprocessor : this.preprocessors) {
      preprocessor.getTransmogrifier().destroy();
    }

    this.correlator.destroy();
  }

  public final void start() {
    this.correlator.start();

    for (final Preprocessor preprocessor : this.preprocessors) {
      preprocessor.getTransmogrifier().start();
    }

    for (final Receiver receiver : this.receivers) {
      receiver.start();
    }
  }

  public final void stop() {
    for (final Receiver receiver : this.receivers) {
      receiver.stop();
    }

    for (final Preprocessor preprocessor : this.preprocessors) {
      preprocessor.getTransmogrifier().stop();
    }

    this.correlator.stop();
  }

}
