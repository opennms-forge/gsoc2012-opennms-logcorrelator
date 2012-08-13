package org.opennms.logcorrelator.preprocessors.opennms;

import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.logcorrelator.api.TransmogrifierFactory;
import org.opennms.logcorrelator.config.xml.PreprocessorConfiguration;


public class NodeAssignmentTransmogrifierFactory implements TransmogrifierFactory {
  @Override
  public Transmogrifier create(final PreprocessorConfiguration configuration) {
    final String baseUrl = configuration.getFirstProperty("baseUrl");

    final String username = configuration.getFirstProperty("username");

    final String password = configuration.getFirstProperty("password");

    final String nodeCriteriaName = configuration.getFirstProperty("nodeCriteriaName",
                                                                   "ipInterface.ipHostName");

    final String messageValueName = configuration.getFirstProperty("messageValueName",
                                                                   "host");

    return new NodeAssignmentTransmogrifier(baseUrl,
                                            username,
                                            password,
                                            nodeCriteriaName,
                                            messageValueName);
  }

}
