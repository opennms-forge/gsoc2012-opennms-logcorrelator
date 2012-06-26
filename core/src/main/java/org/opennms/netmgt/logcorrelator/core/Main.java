package org.opennms.netmgt.logcorrelator.core;

import java.util.Arrays;
import org.opennms.netmgt.logcorrelator.MessageProcessor;
import org.opennms.netmgt.logcorrelator.Preprocessor;
import org.opennms.netmgt.logcorrelator.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		MessageProcessor.instance = new MessageProcessorImpl(Arrays.asList(
				Class.forName(
				"org.opennms.netmgt.logcorrelator.preprocessors.regex.RegexFilterPreprocessor").
				asSubclass(Preprocessor.class).newInstance(),
				Class.forName(
				"org.opennms.netmgt.logcorrelator.preprocessors.regex.RegexReplacePreprocessor").
				asSubclass(Preprocessor.class).newInstance()));

		Receiver receiver = Class.forName(
				"org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogReceiver").
				asSubclass(Receiver.class).newInstance();

		receiver.start();
	}
}
