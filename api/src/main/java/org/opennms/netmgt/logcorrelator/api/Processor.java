package org.opennms.netmgt.logcorrelator.api;

public abstract class Processor {

	public abstract void process(final Message message);

}
