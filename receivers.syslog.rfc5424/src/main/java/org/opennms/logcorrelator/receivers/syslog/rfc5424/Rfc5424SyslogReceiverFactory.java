package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.opennms.logcorrelator.api.Receiver;
import org.opennms.logcorrelator.api.ReceiverFactory;
import org.opennms.logcorrelator.config.ReceiverConfiguration;


public class Rfc5424SyslogReceiverFactory implements ReceiverFactory {
  @Override
  public Receiver create(final MessageFactory messageFactory,
                         final Pipeline pipeline,
                         final ReceiverConfiguration configuration) {
    final String host = configuration.getProperties().getProperty("host");
    final int port = Integer.parseInt(configuration.getProperties().getProperty("port"));

    return new Rfc5424SyslogReceiver(messageFactory,
                                     pipeline,
                                     host,
                                     port);
  }

}
