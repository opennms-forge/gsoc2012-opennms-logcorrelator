package org.opennms.logcorrelator.config.xml;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opennms.logcorrelator.config.PropertiesXmlAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class AbstractPropertyConfiguration {
  @XmlElement(name = "properties")
  @XmlJavaTypeAdapter(value = PropertiesXmlAdapter.class)
  private ListMultimap<String, String> properties = LinkedListMultimap.create();

  public ListMultimap<String, String> getProperties() {
    return this.properties;
  }

  public void setProperties(final ListMultimap<String, String> properties) {
    this.properties = properties;
  }

  public List<String> getProperty(final String key,
                                  final String defaultValue) {
    final List<String> values = this.properties.get(key);

    if (values == null) {
      return Collections.singletonList(defaultValue);
    }

    return values;
  }

  public List<String> getProperty(final String key) {
    return this.getProperty(key,
                            null);
  }

  public String getFirstProperty(final String key,
                                 final String defaultValue) {
    final List<String> values = this.properties.get(key);

    if (values == null || values.isEmpty()) {
      return defaultValue;
    }

    return values.get(0);
  }

  public String getFirstProperty(final String key) {
    return this.getFirstProperty(key,
                                 null);
  }

}
