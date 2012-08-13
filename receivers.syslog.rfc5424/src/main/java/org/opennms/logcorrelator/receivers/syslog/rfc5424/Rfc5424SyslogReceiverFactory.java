package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.opennms.logcorrelator.api.Receiver;
import org.opennms.logcorrelator.api.ReceiverFactory;
import org.opennms.logcorrelator.config.xml.ReceiverConfiguration;


public class Rfc5424SyslogReceiverFactory implements ReceiverFactory {
  @Override
  public Receiver create(final String id,
                         final MessageFactory messageFactory,
                         final Pipeline pipeline,
                         final ReceiverConfiguration configuration) {
    final String host = configuration.getFirstProperty("host");
    final int port = Integer.parseInt(configuration.getFirstProperty("port"));

    return new Rfc5424SyslogReceiver(id,
                                     messageFactory,
                                     pipeline,
                                     host,
                                     port);
  }

}
