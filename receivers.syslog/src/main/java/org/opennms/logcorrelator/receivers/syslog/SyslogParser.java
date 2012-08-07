package org.opennms.logcorrelator.receivers.syslog;

import org.opennms.logcorrelator.api.Message;


/**
 * Interface for sys log message parsers.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public interface SyslogParser {

  public Message parse(final String syslogMessage);

}
