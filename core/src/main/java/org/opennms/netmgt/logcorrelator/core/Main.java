package org.opennms.netmgt.logcorrelator.core;

import org.opennms.netmgt.logcorrelator.MessageProcessor;
import org.opennms.netmgt.logcorrelator.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		MessageProcessor.instance = new MessageProcessorImpl();

		Class<? extends Receiver> receiverClass =
				Class.forName("org.opennms.netmgt.logcorrelator.receivers.syslog.RFC5424.RFC5424SyslogReceiver").
				asSubclass(Receiver.class);
		Receiver receiver = receiverClass.newInstance();

		receiver.start();
	}
}
