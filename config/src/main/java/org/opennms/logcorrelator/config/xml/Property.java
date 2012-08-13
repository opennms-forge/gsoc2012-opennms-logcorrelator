package org.opennms.logcorrelator.config.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {
  @XmlAttribute(name = "key",
                required = true)
  private String key;

  @XmlValue
  private String value;

  public Property() {
  }

  public Property(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return this.key;
  }

  public void setKey(final String key) {
    this.key = key;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(final String value) {
    this.value = value;
  }

}
