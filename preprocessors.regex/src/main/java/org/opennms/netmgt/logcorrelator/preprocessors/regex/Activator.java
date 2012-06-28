package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

	private Logger logger = LoggerFactory.getLogger(Activator.class);

	@Override
	public final void start(final BundleContext context) throws Exception {
		logger.info("start: {}", Activator.class.getName());
	}

	@Override
	public final void stop(final BundleContext context) throws Exception {
		logger.info("stop: {}", Activator.class.getName());
	}

}
