package org.opennms.logcorrelator.config.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class FiltersConfiguration {
  @XmlAttribute(name = "language",
                required = true)
  private String language;

  @XmlElement(name = "filter",
              required = true)
  private List<String> filters = new LinkedList<String>();

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(final String language) {
    this.language = language;
  }

  public List<String> getFilters() {
    return this.filters;
  }

  public void setFilters(final List<String> filters) {
    this.filters = filters;
  }

}
