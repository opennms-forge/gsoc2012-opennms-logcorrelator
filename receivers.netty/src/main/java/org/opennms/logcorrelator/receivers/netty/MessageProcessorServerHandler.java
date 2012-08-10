package org.opennms.logcorrelator.receivers.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageProcessorServerHandler extends SimpleChannelUpstreamHandler {
  private static final Logger logger = LoggerFactory.getLogger(MessageProcessorServerHandler.class);

  private final Pipeline pipeline;

  public MessageProcessorServerHandler(final Pipeline pipeline) {
    this.pipeline = pipeline;
  }

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    final Message message = (Message) e.getMessage();
    logger.debug("Message received from syslog: {}", message);

    this.pipeline.process(message);
  }

}
