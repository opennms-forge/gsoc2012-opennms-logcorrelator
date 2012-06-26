package org.opennms.netmgt.logcorrelator;

public abstract class Processor {
	
	public abstract void process(final Message message);
	
}
