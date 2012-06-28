package org.opennms.netmgt.logcorrelator.correlators.nop;

import org.opennms.netmgt.logcorrelator.api.Message;
import org.opennms.netmgt.logcorrelator.correlators.opennms.AbstractCorrelator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NopCorrelator extends AbstractCorrelator {

	Logger logger = LoggerFactory.getLogger(NopCorrelator.class);

	@Override
	public void process(final Message message) {
		logger.info("Forwarding message to event bus: {}", message);
	}

}
