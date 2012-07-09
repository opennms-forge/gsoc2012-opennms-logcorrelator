package org.opennms.netmgt.logcorrelator.receivers.syslog;

import org.jboss.netty.channel.ChannelPipelineFactory;
import org.opennms.netmgt.logcorrelator.receivers.netty.NettyReceiver;


public abstract class SyslogReceiver extends NettyReceiver {

  public SyslogReceiver(final String host, final int port) {
    super(host, port);
  }
  
  @Override
  protected final ChannelPipelineFactory createPipelineFactory() {
    return new SyslogServerPipelineFactory(this.createSyslogParser());
  }
  
  protected abstract SyslogParser createSyslogParser();

}
