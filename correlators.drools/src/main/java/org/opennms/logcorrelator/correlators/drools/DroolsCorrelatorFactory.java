package org.opennms.logcorrelator.correlators.drools;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.opennms.logcorrelator.api.Correlator;
import org.opennms.logcorrelator.api.CorrelatorFactory;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.config.xml.CorrelatorConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DroolsCorrelatorFactory implements CorrelatorFactory {
  private static Logger logger = LoggerFactory.getLogger(DroolsCorrelatorFactory.class);

  @Override
  public Correlator create(final MessageFactory messageFactory,
                           final CorrelatorConfiguration configuration) {

    configuration.getProperty("url");

    final List<URL> urls = Lists.transform(configuration.getProperty("url"), new Function<String, URL>() {
      @Override
      public URL apply(final String from) {
        try {
          return new URL(from);

        } catch (MalformedURLException ex) {
          logger.error(null, ex);

          throw new RuntimeException(ex);
        }
      }

    });

    return new DroolsCorrelator(messageFactory,
                                urls);
  }

}
