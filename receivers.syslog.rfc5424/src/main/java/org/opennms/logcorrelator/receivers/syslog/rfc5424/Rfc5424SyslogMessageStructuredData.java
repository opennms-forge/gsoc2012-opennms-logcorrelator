package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import java.util.HashMap;
import java.util.Map;


public class Rfc5424SyslogMessageStructuredData extends HashMap<String, Map<String, String>> {
  @Override
  public Map<String, String> get(Object key) {
    Map<String, String> element = super.get(key);

    if (element == null) {
      element = new HashMap<String, String>();
      this.put((String) key, element);
    }

    return element;

  }

}
