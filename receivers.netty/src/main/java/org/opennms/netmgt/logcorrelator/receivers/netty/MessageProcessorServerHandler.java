package org.opennms.netmgt.logcorrelator.receivers.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessorServerHandler extends SimpleChannelUpstreamHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageProcessorServerHandler.class);
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		final Message message = (Message) e.getMessage();
		logger.debug("Message received: {}", message);

		MessageProcessor.instance.process(message);
	}
}
