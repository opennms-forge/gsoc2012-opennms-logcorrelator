package org.opennms.netmgt.logcorrelator.core;

import java.util.List;
import org.opennms.netmgt.logcorrelator.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageProcessorImpl extends MessageProcessor {

    private static final Logger logger = LoggerFactory
	    .getLogger(MessageProcessorImpl.class);

    private Processor processorChain;

    public MessageProcessorImpl(final List<Preprocessor> preprocessors,
	    final Correlator correlator) {
	Preprocessor last = null;
	for (Preprocessor preprocessor : preprocessors) {
	    if (last != null)
		last.setNextProcessor(preprocessor);
	    else
		processorChain = preprocessor;

	    last = preprocessor;
	}

	last.setNextProcessor(correlator);
    }

    @Override
    public void process(final Message message) {
	logger.info("Processing message: {}", message);

	this.processorChain.process(message);
    }
}
