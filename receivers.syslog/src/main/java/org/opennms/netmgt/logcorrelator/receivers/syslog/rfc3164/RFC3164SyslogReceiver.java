package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc3164;

import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogReceiver;

public class RFC3164SyslogReceiver extends SyslogReceiver {

	@Override
	protected SyslogParser createSyslogParser() {
		return new RFC3164SyslogParser();
	}
}
