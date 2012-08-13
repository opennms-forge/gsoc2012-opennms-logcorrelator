package org.opennms.logcorrelator.api;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.opennms.logcorrelator.config.xml.CorrelatorConfiguration;
import org.opennms.logcorrelator.config.xml.FiltersConfiguration;
import org.opennms.logcorrelator.config.xml.PreprocessorConfiguration;
import org.opennms.logcorrelator.config.xml.ReceiverConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PluginFactory {
  private final static Logger logger = LoggerFactory.getLogger(PluginFactory.class);

  public static Correlator createCorrelator(final MessageFactory messageFactory,
                                            final CorrelatorConfiguration config) throws Exception {
    final Class<? extends CorrelatorFactory> factoryClass = Class.forName(config.getType()).asSubclass(CorrelatorFactory.class);
    final CorrelatorFactory factory = factoryClass.newInstance();

    return factory.create(messageFactory,
                          config);
  }

  public static Set<Filter> createFilters(final FiltersConfiguration config) throws Exception {
    if (config == null) {
      return Collections.emptySet();
    }

    final Class<? extends FilterFactory> factoryClass = Class.forName(config.getLanguage()).asSubclass(FilterFactory.class);
    final FilterFactory factory = factoryClass.newInstance();

    final Set<Filter> filters = new HashSet<Filter>();

    for (final String expression : config.getFilters()) {
      final Filter filter = factory.create(expression);
      filters.add(filter);
    }

    return filters;
  }

  public static Preprocessor createPreprocessor(final MessageFactory messageFactory,
                                                final PreprocessorConfiguration config) throws Exception {
    final Class<? extends TransmogrifierFactory> factoryClass = Class.forName(config.getType()).asSubclass(TransmogrifierFactory.class);
    final TransmogrifierFactory factory = factoryClass.newInstance();

    final Transmogrifier transmogrifier = factory.create(config);

    return new Preprocessor(config.getId(),
                            messageFactory,
                            createFilters(config.getFilters()),
                            transmogrifier);
  }

  public static Receiver createReceiver(final Pipeline pipeline,
                                        final MessageFactory messageFactory,
                                        final ReceiverConfiguration config) throws Exception {
    final Class<? extends ReceiverFactory> factoryClass = Class.forName(config.getType()).asSubclass(ReceiverFactory.class);
    final ReceiverFactory factory = factoryClass.newInstance();

    return factory.create(config.getId(),
                          messageFactory,
                          pipeline,
                          config);
  }

}
