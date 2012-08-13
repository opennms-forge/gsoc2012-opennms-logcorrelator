package org.opennms.logcorrelator.config;

import org.opennms.logcorrelator.config.xml.Properties;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.opennms.logcorrelator.config.xml.Property;


public class PropertiesXmlAdapter extends XmlAdapter<Properties, Map<String, String>> {
  @Override
  public final Map<String, String> unmarshal(final Properties l) throws Exception {
    final Map<String, String> m = new HashMap<String, String>();

    for (final Property p : l.getProperties()) {
      m.put(p.getKey(), p.getValue());
    }

    return m;
  }

  @Override
  public final Properties marshal(final Map<String, String> m) throws Exception {
    final Properties l = new Properties();

    for (final Map.Entry<String, String> e : m.entrySet()) {
      l.getProperties().add(new Property(e.getKey(), e.getValue()));
    }

    return l;
  }

}
