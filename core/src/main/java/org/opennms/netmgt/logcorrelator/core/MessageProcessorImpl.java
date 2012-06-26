package org.opennms.netmgt.logcorrelator.core;

import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessorImpl extends MessageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(MessageProcessorImpl.class);
	
	@Override
	public void process(final Message message) {
		logger.debug("Processing message: {}", message);
		
		
	}
	
}
