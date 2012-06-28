package org.opennms.netmgt.logcorrelator.core;

import org.opennms.netmgt.logcorrelator.api.CorrelatorFactory;
import org.opennms.netmgt.logcorrelator.api.PreprocessorFactory;
import org.opennms.netmgt.logcorrelator.api.ReceiverFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

    private Logger logger = LoggerFactory.getLogger(Activator.class);

    @Override
	public final void start(final BundleContext context) throws Exception {
		logger.info("start: {}", Activator.class.getName());

		logger.info("receiver: {}", context.getServiceReferences(ReceiverFactory.class.getName() , null));

		logger.info("preprocessor: {}", context.getServiceReferences(PreprocessorFactory.class.getName(), null));

		logger.info("correlator: {}", context.getServiceReferences(CorrelatorFactory.class.getName(), null));
	}

    @Override
    public final void stop(final BundleContext context) throws Exception {
	logger.info("stop: {}", Activator.class.getName());
    }

}
