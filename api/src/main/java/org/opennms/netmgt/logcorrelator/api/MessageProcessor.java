package org.opennms.netmgt.logcorrelator.api;

public abstract class MessageProcessor {

	public static MessageProcessor instance;

	public abstract void process(final Message message);
}
