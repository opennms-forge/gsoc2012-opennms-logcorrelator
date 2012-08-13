package org.opennms.logcorrelator.config.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class ReceiverConfiguration extends AbstractPluginConfiguration {

  @XmlAttribute(name = "id",
                required = true)
  private String id;

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

}
