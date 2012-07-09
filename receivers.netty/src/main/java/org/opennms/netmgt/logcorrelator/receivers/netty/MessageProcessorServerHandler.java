package org.opennms.netmgt.logcorrelator.receivers.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.opennms.netmgt.logcorrelator.api.Message;
import org.opennms.netmgt.logcorrelator.api.MessageProcessor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessorServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = LoggerFactory
	    .getLogger(MessageProcessorServerHandler.class);

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
	    throws Exception {
	final Message message = (Message) e.getMessage();
	logger.info("Message received: {}", message);

	BundleContext context = FrameworkUtil.getBundle(
		MessageProcessorServerHandler.class).getBundleContext();

	ServiceReference reference = context
		.getServiceReference(MessageProcessor.class.getName());
	MessageProcessor processor = (MessageProcessor) context
		.getService(reference);

	processor.process(message);
    }
}
