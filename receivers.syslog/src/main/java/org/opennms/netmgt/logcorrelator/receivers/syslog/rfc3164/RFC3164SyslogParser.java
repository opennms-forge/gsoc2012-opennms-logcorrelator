package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc3164;

import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.SimpleMessage;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A message implementation representing a RFC5424 Syslog message.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public class RFC3164SyslogParser implements SyslogParser {

	private static Logger logger = LoggerFactory.getLogger(
			RFC3164SyslogParser.class);

	public RFC3164SyslogParser() {
	}

	@Override
	public Message parse(final String syslogMessage) {
		logger.debug("Syslog message received: {}", syslogMessage);

		Message message = new SimpleMessage();

		try {
//			Parser.parse("SYSLOG-MSG", syslogMessage);

		} catch (Exception ex) {
			logger.error("Failed to parse RFC3164 syslog message: {}",
						 syslogMessage, ex);

			throw new RuntimeException(ex);
		}

		return message;
	}
}
