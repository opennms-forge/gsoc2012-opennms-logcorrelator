package org.opennms.logcorrelator.receivers.flume;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlumeHeaders extends HashMap<String, String> {
  public FlumeHeaders(Map<CharSequence, CharSequence> headers) {
    for (Map.Entry<CharSequence, CharSequence> entry : headers.entrySet()) {
      this.put(entry.getKey().toString(), entry.getValue().toString());
    }
  }

}
