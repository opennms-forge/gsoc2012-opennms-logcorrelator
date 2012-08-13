package org.opennms.logcorrelator.config.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.FIELD)
public class Configuration {
  
  @XmlElement(name = "receiver",
              required = true)
  private List<ReceiverConfiguration> receivers = new ArrayList<ReceiverConfiguration>();
  
  @XmlElement(name = "preprocessor",
              required = false)
  private List<PreprocessorConfiguration> preprocessors = new ArrayList<PreprocessorConfiguration>();

  @XmlElement(name = "correlator",
              required = true)
  private CorrelatorConfiguration correlator = null;

  public List<ReceiverConfiguration> getReceivers() {
    return this.receivers;
  }

  public void setReceivers(final List<ReceiverConfiguration> receivers) {
    this.receivers = receivers;
  }

  public List<PreprocessorConfiguration> getPreprocessors() {
    return this.preprocessors;
  }

  public void setPreprocessors(final List<PreprocessorConfiguration> preprocessors) {
    this.preprocessors = preprocessors;
  }

  public CorrelatorConfiguration getCorrelator() {
    return this.correlator;
  }
  
  public void setCorrelator(final CorrelatorConfiguration correlator) {
    this.correlator = correlator;
  }

}
