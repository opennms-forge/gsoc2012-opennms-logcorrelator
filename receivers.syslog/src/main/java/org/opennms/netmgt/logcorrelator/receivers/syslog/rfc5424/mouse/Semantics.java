package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424.mouse;

import com.doitnext.mouse.runtime.SemanticsBase;
import java.util.Calendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.SimpleMessage;
import org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogParser;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;

public class Semantics extends SemanticsBase {

	private Message message = new SimpleMessage();

	boolean pri() {
		final int pri = Integer.parseInt(rhsText(0, rhsSize()));

		final SyslogParser.Facility facility = SyslogParser.Facility.values()[pri /
																			  8];
		final SyslogParser.Severity severity = SyslogParser.Severity.values()[pri %
																			  8];

		this.message.setHeader(SyslogParser.HEADER_FACILITY, facility);
		this.message.setHeader(SyslogParser.HEADER_SEVERITY, severity);

		return true;
	}

	boolean version() {
		final int version = Integer.parseInt(rhsText(0, rhsSize()));

		this.message.setHeader(Rfc5424SyslogParser.HEADER_PROTOCOL_VERSION, version);

		return true;
	}

	boolean timestamp() {
		final String s = lhs().text();

		try {
			final Calendar timestamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(
					s).toGregorianCalendar();

			this.message.setHeader(SyslogParser.HEADER_TIMESTAMP,
								   timestamp);

		} catch (DatatypeConfigurationException ex) {
			return false;
		}

		return true;
	}

	boolean hostname() {
		this.message.setHeader(SyslogParser.HEADER_HOST,
							   rhsText(0, rhsSize()));

		return true;
	}

	boolean application() {
		this.message.setHeader(Rfc5424SyslogParser.HEADER_APPLICATION,
							   rhsText(0, rhsSize()));

		return true;
	}

	boolean processId() {
		this.message.setHeader(Rfc5424SyslogParser.HEADER_PROCESS_ID,
							   rhsText(0, rhsSize()));

		return true;
	}

	boolean messageId() {
		this.message.setHeader(Rfc5424SyslogParser.HEADER_MESSAGE_ID,
							   rhsText(0, rhsSize()));

		return true;
	}

	boolean structuredDataElement() {
		final String base = rhs(1).text();

		for (int i = 3; i < rhsSize(); i += 5) {
			final String key = rhs(i + 0).text();
			final Object value = rhs(i + 2).get();

			this.message.setHeader(base + "." + key, value);
		}

		return true;
	}

	boolean structuredDataParamValue() {
		String text = rhsText(0, rhsSize());

		text = text.replace("\\\"", "\"");
		text = text.replace("\\\\", "\\");
		text = text.replace("\\]", "]");

		lhs().put(text);

		return true;
	}

	boolean body() {
		this.message.setBody(rhsText(0, rhsSize()));

		return true;
	}

	public Message getMessage() {
		return this.message;
	}
}
