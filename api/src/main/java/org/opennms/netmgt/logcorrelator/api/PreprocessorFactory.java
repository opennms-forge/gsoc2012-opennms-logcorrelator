package org.opennms.netmgt.logcorrelator.api;

import java.util.Properties;

public interface PreprocessorFactory {

	public abstract Preprocessor create(final Properties properties);

}
