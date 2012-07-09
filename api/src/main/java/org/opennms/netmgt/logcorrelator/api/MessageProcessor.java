package org.opennms.netmgt.logcorrelator.api;

public abstract class MessageProcessor extends Processor {

    public abstract void process(final Message message);
}
