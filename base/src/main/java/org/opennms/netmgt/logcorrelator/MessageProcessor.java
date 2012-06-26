package org.opennms.netmgt.logcorrelator;

public abstract class MessageProcessor {
	
	public static MessageProcessor instance;

	public abstract void process(final Message message);
}
