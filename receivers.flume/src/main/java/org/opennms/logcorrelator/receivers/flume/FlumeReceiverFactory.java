package org.opennms.logcorrelator.receivers.flume;

import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.opennms.logcorrelator.api.Receiver;
import org.opennms.logcorrelator.api.ReceiverFactory;
import org.opennms.logcorrelator.config.xml.ReceiverConfiguration;


public class FlumeReceiverFactory implements ReceiverFactory {
  @Override
  public Receiver create(final String id,
                         final MessageFactory messageFactory,
                         final Pipeline pipeline,
                         final ReceiverConfiguration configuration) {
    final String host = configuration.getFirstProperty("host");
    final int port = Integer.parseInt(configuration.getFirstProperty("port"));

    return new FlumeReceiver(id,
                             messageFactory,
                             pipeline,
                             host,
                             port);
  }

}
