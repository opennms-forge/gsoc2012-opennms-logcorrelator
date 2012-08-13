package org.opennms.logcorrelator.config.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;


@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractPluginConfiguration extends AbstractPropertyConfiguration {
  @XmlAttribute(name = "type",
                required = true)
  private String type;

  public String getType() {
    return this.type;
  }

  public void setType(final String type) {
    this.type = type;
  }

}
