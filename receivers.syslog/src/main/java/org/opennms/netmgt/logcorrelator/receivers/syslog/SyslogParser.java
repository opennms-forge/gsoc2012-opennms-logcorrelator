package org.opennms.netmgt.logcorrelator.receivers.syslog;

import org.opennms.netmgt.logcorrelator.Message;

/**
 * Interface for sys log message parsers.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public interface SyslogParser {

	public final static String HEADER_FACILITY = "facility";

	public final static String HEADER_SEVERITY = "severity";

	public final static String HEADER_TIMESTAMP = "timestamp";

	public final static String HEADER_HOST = "host";

	public final static String HEADER_TAG = "tag";

	public static enum Facility {

		KERNEL,
		USER,
		MAIL,
		SYSTEM,
		SECURITY,
		SYSLOG,
		PRINTER,
		NETWORK,
		UUCP,
		CLOCK,
		FTP,
		NTP,
		LOCAL_0,
		LOCAL_1,
		LOCAL_2,
		LOCAL_3,
		LOCAL_4,
		LOCAL_5,
		LOCAL_6,
		LOCAL_7
	}

	public static enum Severity {
		EMERGENCY,
		ALERT,
		CRITICAL,
		ERROR,
		WARNING,
		NOTICE,
		INFORMATIONAL,
		DEBUG
	}

	public Message parse(final String syslogMessage);
}
