package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424;

import com.doitnext.mouse.runtime.SourceString;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424.mouse.Parser;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A message implementation representing a RFC5424 Syslog message.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public class Rfc5424SyslogParser implements SyslogParser {

	public final static String HEADER_PROTOCOL_VERSION = "Rfc5424ProtocolVersion";

	public final static String HEADER_APPLICATION = "application";

	public final static String HEADER_PROCESS_ID = "processId";

	public final static String HEADER_MESSAGE_ID = "messageId";

	private static final Logger logger = LoggerFactory.getLogger(
			Rfc5424SyslogParser.class);

	@Override
	public Message parse(String syslogMessage) {
		logger.debug("Parsing RFC5424 syslog message: {}",
					 syslogMessage);

		final Parser parser = new Parser();

		if (parser.parse(new SourceString(syslogMessage))) {
			return parser.semantics().getMessage();
		}

		return null;
	}
}
