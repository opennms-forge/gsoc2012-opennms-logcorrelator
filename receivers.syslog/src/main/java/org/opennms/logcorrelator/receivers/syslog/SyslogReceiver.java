package org.opennms.logcorrelator.receivers.syslog;

import java.util.Calendar;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.opennms.logcorrelator.receivers.netty.NettyReceiver;


public abstract class SyslogReceiver extends NettyReceiver {
  public MessageAccessor<SyslogMessageFacility> FACILITY;

  public MessageAccessor<SyslogMessageSeverity> SEVERITY;

  public MessageAccessor<Calendar> TIMESTAMP;

  public MessageAccessor<String> HOST;

  public MessageAccessor<String> BODY;

  private final Pipeline pipeline;

  public SyslogReceiver(final String id,
                        final MessageFactory messageFactory,
                        final Pipeline pipeline,
                        final String host,
                        final int port) {
    super(id,
          messageFactory,
          host,
          port);

    this.pipeline = pipeline;
  }

  @Override
  protected final ChannelPipelineFactory createPipelineFactory() {
    return new SyslogServerPipelineFactory(this.pipeline,
                                           this.createSyslogParser());
  }

  protected abstract SyslogParser createSyslogParser();

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
    super.registerMessageDeclaration(declarator);
    
    this.FACILITY = declarator.registerField("facility", SyslogMessageFacility.class);
    this.SEVERITY = declarator.registerField("severity", SyslogMessageSeverity.class);
    this.TIMESTAMP = declarator.registerField("timestamp", Calendar.class);
    this.HOST = declarator.registerField("host", String.class);
    this.BODY = declarator.registerField("body", String.class);
  }

}
