package org.opennms.logcorrelator.config;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import java.util.Map;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.opennms.logcorrelator.config.xml.Properties;
import org.opennms.logcorrelator.config.xml.Property;


public class PropertiesXmlAdapter extends XmlAdapter<Properties, Multimap<String, String>> {
  @Override
  public final Multimap<String, String> unmarshal(final Properties l) throws Exception {
    final Multimap<String, String> m = LinkedListMultimap.create();

    for (final Property p : l.getProperties()) {
      m.put(p.getKey(), p.getValue());
    }

    return m;
  }

  @Override
  public final Properties marshal(final Multimap<String, String> m) throws Exception {
    final Properties l = new Properties();

    for (final Map.Entry<String, String> e : m.entries()) {
      l.getProperties().add(new Property(e.getKey(), e.getValue()));
    }

    return l;
  }

}
