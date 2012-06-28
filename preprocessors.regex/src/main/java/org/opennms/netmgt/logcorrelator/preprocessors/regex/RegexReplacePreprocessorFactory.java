package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import java.util.Properties;

public class RegexReplacePreprocessorFactory implements org.opennms.netmgt.logcorrelator.api.PreprocessorFactory {

  @Override
  public org.opennms.netmgt.logcorrelator.api.Preprocessor create(Properties properties) {
    return new RegexReplacePreprocessor("", "", "");
  }
  
}
