package org.opennms.logcorrelator.config.xml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.opennms.logcorrelator.config.PropertiesXmlAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public abstract class AbstractPropertyConfiguration {
  @XmlElement(name = "properties")
  @XmlJavaTypeAdapter(value = PropertiesXmlAdapter.class)
  private Map<String, String> properties = new HashMap<String, String>();

  public Map<String, String> getProperties() {
    return this.properties;
  }

  public void setProperties(final Map<String, String> properties) {
    this.properties = properties;
  }

  public String getProperty(final String key) {
    return this.properties.get(key);
  }

  public String getProperty(final String key,
                                  final String defaultValue) {
    final String value = this.properties.get(key);

    if (value == null) {
      return defaultValue;
    }

    return value;
  }

}
