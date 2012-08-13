package org.opennms.logcorrelator.config.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class PreprocessorConfiguration extends AbstractPluginConfiguration {
  private final static Logger logger = LoggerFactory.getLogger(PreprocessorConfiguration.class);

  @XmlAttribute(name = "id",
                required = true)
  private String id;

  @XmlElement(name = "filters",
              required = false)
  private FiltersConfiguration filters = null;

  public String getId() {
    return this.id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public FiltersConfiguration getFilters() {
    return this.filters;
  }

  public void setFilters(final FiltersConfiguration filters) {
    this.filters = filters;
  }

}
