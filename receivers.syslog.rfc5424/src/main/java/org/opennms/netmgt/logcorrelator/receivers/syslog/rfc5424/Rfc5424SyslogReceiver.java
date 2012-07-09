package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424;

import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogReceiver;

public class Rfc5424SyslogReceiver extends SyslogReceiver {

  public Rfc5424SyslogReceiver(final String host, final int port) {
    super(host, port);
  }
  
	@Override
	protected SyslogParser createSyslogParser() {
		return new Rfc5424SyslogParser();
	}
}
