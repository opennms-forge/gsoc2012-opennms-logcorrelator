package org.opennms.netmgt.logcorrelator.api;

import java.util.Properties;

public interface CorrelatorFactory {

	public abstract Correlator create(final Properties properties);

}
