package org.opennms.logcorrelator.correlators.drools;

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
import org.drools.io.impl.ClassPathResource;
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
  
  private StatefulKnowledgeSession knowledgeSession;

  private Thread thread;

  public DroolsCorrelator(final MessageFactory messageFactory) {
    this.messageFactory = messageFactory;
    
    this.thread = new Thread(this);
  }


  @Override
  public void init() {
    logger.debug("Initializing Drools Correlator");
    
    ClassLoader messageClassLoader = this.messageFactory.getMessageClass().getClassLoader();
    
    KnowledgeBuilderConfiguration builderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null,
                                                                                                                  messageClassLoader);
    KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder(builderConfiguration);
    builder.add(new ClassPathResource("test.drl", getClass()), ResourceType.DRL);

    if (builder.hasErrors()) {
      for (KnowledgeBuilderError error : builder.getErrors()) {
        logger.error(error.getMessage());
      }
    }

    KnowledgeBaseConfiguration baseConfig = KnowledgeBaseFactory.newKnowledgeBaseConfiguration(null,
                                                                                               messageClassLoader);
    baseConfig.setOption(EventProcessingOption.STREAM);

    KnowledgeBase base = KnowledgeBaseFactory.newKnowledgeBase(baseConfig);
    
    base.addKnowledgePackages(builder.getKnowledgePackages());

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
