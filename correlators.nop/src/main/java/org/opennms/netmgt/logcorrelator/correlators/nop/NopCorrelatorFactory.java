package org.opennms.netmgt.logcorrelator.correlators.nop;

import java.util.Properties;

public class NopCorrelatorFactory implements org.opennms.netmgt.logcorrelator.api.CorrelatorFactory {

  @Override
  public org.opennms.netmgt.logcorrelator.api.Correlator create(Properties properties) {
    return new NopCorrelator();
  }
  
}
