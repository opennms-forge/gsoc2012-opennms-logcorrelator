package org.opennms.netmgt.logcorrelator.core;

import java.util.List;
import org.opennms.netmgt.logcorrelator.api.Message;
import org.opennms.netmgt.logcorrelator.api.MessageProcessor;
import org.opennms.netmgt.logcorrelator.api.Preprocessor;
import org.opennms.netmgt.logcorrelator.api.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessorImpl extends MessageProcessor {

	private static final Logger logger = LoggerFactory.getLogger(MessageProcessorImpl.class);

	private Processor processorChain;

	public MessageProcessorImpl(final List<Preprocessor> preprocessors) {
		Preprocessor last = null;
		for (Preprocessor preprocessor : preprocessors) {
			if (last != null)
				last.setNextProcessor(preprocessor);

			last = preprocessor;
		}
	}

	@Override
	public void process(final Message message) {
		logger.info("Processing message: {}", message);

		this.processorChain.process(message);
	}

}
