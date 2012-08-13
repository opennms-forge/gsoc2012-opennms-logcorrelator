package org.opennms.logcorrelator.config.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Properties {
  @XmlElement(name = "property")
  private List<Property> properties = new LinkedList<Property>();

  public Properties() {
  }

  public List<Property> getProperties() {
    return this.properties;
  }

  public void setProperties(final List<Property> properties) {
    this.properties = properties;
  }

}
