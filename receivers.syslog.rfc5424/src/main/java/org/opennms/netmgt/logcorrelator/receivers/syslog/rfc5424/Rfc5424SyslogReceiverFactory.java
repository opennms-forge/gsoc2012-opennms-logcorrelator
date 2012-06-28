package org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424;

import java.util.Properties;

public class Rfc5424SyslogReceiverFactory implements org.opennms.netmgt.logcorrelator.api.ReceiverFactory {

  @Override
  public org.opennms.netmgt.logcorrelator.api.Receiver create(Properties properties) {
    return new Rfc5424SyslogReceiver();
  }
  
}
