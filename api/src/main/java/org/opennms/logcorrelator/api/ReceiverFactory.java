package org.opennms.logcorrelator.api;

import org.opennms.logcorrelator.config.xml.ReceiverConfiguration;


public interface ReceiverFactory {
  public abstract Receiver create(final String id,
                                  final MessageFactory messageFactory,
                                  final Pipeline pipeline,
                                  final ReceiverConfiguration configuration);

}
