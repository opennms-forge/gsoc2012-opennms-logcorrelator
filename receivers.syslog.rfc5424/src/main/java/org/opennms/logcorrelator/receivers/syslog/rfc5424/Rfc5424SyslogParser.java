package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import mouse.runtime.SourceString;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.receivers.syslog.SyslogParser;
import org.opennms.logcorrelator.receivers.syslog.rfc5424.mouse.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A message implementation representing a RFC5424 Syslog message.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public class Rfc5424SyslogParser implements SyslogParser {
  private static final Logger logger = LoggerFactory.getLogger(Rfc5424SyslogParser.class);

  private Rfc5424SyslogReceiver receiver;

  public Rfc5424SyslogParser(final Rfc5424SyslogReceiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public Message parse(String syslogMessage) {
    logger.debug("Parsing RFC5424 syslog message: {}", syslogMessage);

    final Parser parser = new Parser();
    parser.semantics().receiver = this.receiver;
    parser.semantics().message = this.receiver.getMessageFactory().createMessage();

    if (parser.parse(new SourceString(syslogMessage))) {
      return parser.semantics().message;
    }

    return null;
  }

}
