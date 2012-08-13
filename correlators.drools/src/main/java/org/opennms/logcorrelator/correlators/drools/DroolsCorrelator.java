package org.opennms.logcorrelator.correlators.drools;

import java.net.URL;
import java.util.List;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.StatefulKnowledgeSession;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.correlators.opennms.AbstractCorrelator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DroolsCorrelator extends AbstractCorrelator implements Runnable {
  private final static Logger logger = LoggerFactory.getLogger(DroolsCorrelator.class);

  private final MessageFactory messageFactory;

  private final List<URL> urls;

  private StatefulKnowledgeSession knowledgeSession;

  private Thread thread;

  public DroolsCorrelator(final MessageFactory messageFactory,
                          final List<URL> urls) {
    this.messageFactory = messageFactory;

    this.urls = urls;

    this.thread = new Thread(this);
  }

  @Override
  public void init() {
    logger.debug("Initializing Drools Correlator");

    // Get class loader of message implementation
    final ClassLoader messageClassLoader = this.messageFactory.getMessageClass().getClassLoader();

    // Create the knowledge builder
    final KnowledgeBuilderConfiguration builderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null,
                                                                                                                        messageClassLoader);
    final KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder(builderConfiguration);

    // Add URLs to the knowledge builder
    for (final URL url : this.urls) {
      builder.add(new UrlResource(url),
                  ResourceType.DRL);

      if (builder.hasErrors()) {
        for (KnowledgeBuilderError error : builder.getErrors()) {
          logger.error("Error while loading {}: {}", url, error.getMessage());
        }
      }
    }

    // Create the knowledge base
    final KnowledgeBaseConfiguration baseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(null,
                                                                                                     messageClassLoader);
    baseConfig.setOption(EventProcessingOption.STREAM);

    final KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase(baseConfig);
    base.addKnowledgePackages(builder.getKnowledgePackages());

    // Create knowledge session
    this.knowledgeSession = base.newStatefulKnowledgeSession();
    this.knowledgeSession.addEventListener(new DebugAgendaEventListener());
    this.knowledgeSession.addEventListener(new DebugWorkingMemoryEventListener());
  }

  @Override
  public void process(final Message message) {
    logger.debug("Inserting message into Drools knowledge base: '{}'", message);

    this.knowledgeSession.insert(message);
  }

  @Override
  public void destroy() {
    this.knowledgeSession.dispose();
  }

  @Override
  public void start() {
    this.thread.start();
  }

  @Override
  public void stop() {
    this.knowledgeSession.halt();
  }

  @Override
  public void run() {
    this.knowledgeSession.fireUntilHalt();

    logger.error("Drools engine finished");
  }

  @Override
  public final String getId() {
    return "drools";
  }

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
  }

}
