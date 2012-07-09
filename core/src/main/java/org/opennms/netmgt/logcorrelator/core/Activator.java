package org.opennms.netmgt.logcorrelator.core;

import java.util.Arrays;
import java.util.Properties;

import org.opennms.netmgt.logcorrelator.api.Correlator;
import org.opennms.netmgt.logcorrelator.api.CorrelatorFactory;
import org.opennms.netmgt.logcorrelator.api.MessageProcessor;
import org.opennms.netmgt.logcorrelator.api.Preprocessor;
import org.opennms.netmgt.logcorrelator.api.PreprocessorFactory;
import org.opennms.netmgt.logcorrelator.api.Receiver;
import org.opennms.netmgt.logcorrelator.api.ReceiverFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

    private final static Logger logger = LoggerFactory
	    .getLogger(Activator.class);

    public static <T> T foo(final Class<T> clazz, final String name) {
	BundleContext bc = FrameworkUtil.getBundle(Activator.class)
		.getBundleContext();

	try {
	    final Filter filter = bc.createFilter("(objectclass="
		    + clazz.getName() + ")");

	    ServiceTracker tracker = new ServiceTracker(bc, filter, null);
	    tracker.open();

	    tracker.waitForService(10000);

	    T t = (T) tracker.getService();

	    tracker.close();

	    return t;

	} catch (Exception ex) {
	    logger.error("Error while loading plugin: {}", ex);
	    return null;
	}
    }

    @Override
    public final void start(final BundleContext context) throws Exception {
	logger.info("start: {}", Activator.class.getName());

	ReceiverFactory rf = foo(ReceiverFactory.class, "syslog-rfc5424");
	PreprocessorFactory pf = foo(PreprocessorFactory.class, "regex-filter");
	CorrelatorFactory cf = foo(CorrelatorFactory.class, "nop");
	logger.debug("Factories received");

	Properties rp = new Properties();
	rp.put("host", "localhost");
	rp.put("port", "10514");
	Receiver r = rf.create(rp);
	logger.debug("Receiver created");

	Properties pp = new Properties();
	Preprocessor p = pf.create(pp);
	logger.debug("Preprocessor created");

	Properties cp = new Properties();
	Correlator c = cf.create(cp);
	logger.debug("Correlator created");

	MessageProcessor messageProcessor = new MessageProcessorImpl(
		Arrays.asList(p), c);
	context.registerService(MessageProcessor.class.getName(),
		messageProcessor, null);
	logger.debug("Message processor registered");

	r.start();
	logger.debug("Receiver started");
    }

    @Override
    public final void stop(final BundleContext context) throws Exception {
	logger.info("stop: {}", Activator.class.getName());
    }
}
