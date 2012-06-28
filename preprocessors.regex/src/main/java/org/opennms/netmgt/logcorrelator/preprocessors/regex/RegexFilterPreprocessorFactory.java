package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import java.util.Properties;
import org.opennms.netmgt.logcorrelator.api.Preprocessor;

public class RegexFilterPreprocessorFactory implements org.opennms.netmgt.logcorrelator.api.PreprocessorFactory {

  @Override
  public Preprocessor create(Properties properties) {
    return new RegexFilterPreprocessor("", "");
  }
  
}
