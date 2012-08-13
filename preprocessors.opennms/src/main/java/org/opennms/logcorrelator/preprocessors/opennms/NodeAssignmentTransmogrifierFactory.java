package org.opennms.logcorrelator.preprocessors.opennms;

import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.logcorrelator.api.TransmogrifierFactory;
import org.opennms.logcorrelator.config.xml.PreprocessorConfiguration;


public class NodeAssignmentTransmogrifierFactory implements TransmogrifierFactory {
  @Override
  public Transmogrifier create(final PreprocessorConfiguration configuration) {
    final String baseUrl = configuration.getProperty("baseUrl");

    final String username = configuration.getProperty("username");

    final String password = configuration.getProperty("password");

    final String nodeCriteriaName = configuration.getProperty("nodeCriteriaName",
                                                              "ipInterface.ipHostName");

    final String messageValueName = configuration.getProperty("messageValueName",
                                                              "host");

    return new NodeAssignmentTransmogrifier(baseUrl,
                                            username,
                                            password,
                                            nodeCriteriaName,
                                            messageValueName);
  }

}
