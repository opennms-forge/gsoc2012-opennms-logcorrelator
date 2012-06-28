package org.opennms.netmgt.logcorrelator.api;

import java.util.Properties;

public interface ReceiverFactory {

	public abstract Receiver create(final Properties properties);

}
