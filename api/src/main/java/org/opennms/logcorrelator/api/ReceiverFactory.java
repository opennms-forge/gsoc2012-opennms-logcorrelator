package org.opennms.logcorrelator.api;

import org.opennms.logcorrelator.config.ReceiverConfiguration;


public interface ReceiverFactory {
  public abstract Receiver create(final String id,
                                  final MessageFactory messageFactory,
                                  final Pipeline pipeline,
                                  final ReceiverConfiguration configuration);

}
