package org.opennms.logcorrelator.preprocessors.opennms;

import org.opennms.logcorrelator.api.Transmogrifier;
import org.opennms.logcorrelator.api.TransmogrifierFactory;
import org.opennms.logcorrelator.config.PreprocessorConfiguration;


public class NodeAssignmentTransmogrifierFactory implements TransmogrifierFactory {
  @Override
  public Transmogrifier create(final PreprocessorConfiguration configuration) {
    final String baseUrl = configuration.getProperties().getProperty("baseUrl");

    final String username = configuration.getProperties().getProperty("username");
    
    final String password = configuration.getProperties().getProperty("password");

    final String nodeCriteriaName = configuration.getProperties().getProperty("nodeCriteriaName",
                                                                              "ipInterface.ipHostName");
    
    final String messageValueName = configuration.getProperties().getProperty("messageValueName",
                                                                              "host");

    return new NodeAssignmentTransmogrifier(baseUrl,
                                            username,
                                            password,
                                            nodeCriteriaName,
                                            messageValueName);
  }

}
