package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.opennms.logcorrelator.receivers.syslog.SyslogParser;
import org.opennms.logcorrelator.receivers.syslog.SyslogReceiver;


public class Rfc5424SyslogReceiver extends SyslogReceiver {
  public MessageAccessor<Integer> PROTOCOLL_VERSION;

  public MessageAccessor<String> APPLICATION;

  public MessageAccessor<String> PROCESS_ID;

  public MessageAccessor<Rfc5424SyslogMessageStructuredData> STRUCTURED_DATA;
  
  public MessageAccessor<String> MESSAGE_ID;

  private final MessageFactory messageFactory;

  public Rfc5424SyslogReceiver(final MessageFactory messageFactory,
                               final Pipeline pipeline,
                               final String host,
                               final int port) {
    super(pipeline,
          host,
          port);

    this.messageFactory = messageFactory;
  }

  @Override
  protected SyslogParser createSyslogParser() {
    return new Rfc5424SyslogParser(this);
  }

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
    super.registerMessageDeclaration(declarator);

    this.PROTOCOLL_VERSION = declarator.registerField("rfc5424ProtocolVersion", Integer.class);
    this.APPLICATION = declarator.registerField("application", String.class);
    this.PROCESS_ID = declarator.registerField("processId", String.class);
    this.MESSAGE_ID = declarator.registerField("messageId", String.class);
    this.STRUCTURED_DATA = declarator.registerField("structuredData", Rfc5424SyslogMessageStructuredData.class);
  }

  public MessageFactory getMessageFactory() {
    return this.messageFactory;
  }
}
