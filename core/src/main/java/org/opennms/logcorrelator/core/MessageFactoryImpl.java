package org.opennms.logcorrelator.core;

import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageDeclarationProvider;
import org.opennms.logcorrelator.api.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageFactoryImpl implements MessageFactory {
  private final static Logger logger = LoggerFactory.getLogger(MessageFactoryImpl.class);

  private final MessageDeclaratorImpl declarator;

  private Class<Message> messageClass;

  public MessageFactoryImpl() {
    this.declarator = new MessageDeclaratorImpl();
  }

  public final void register(final MessageDeclarationProvider declarationProvider) {
    if (this.messageClass != null) {
      throw new IllegalStateException("Message class already created");
    }

    declarationProvider.registerMessageDeclaration(this.declarator);
  }

  public final void produce() {
    if (this.messageClass != null) {
      throw new IllegalStateException("Message class already created");
    }

    this.messageClass = this.declarator.createMessageImplementationClass();
  }

  @Override
  public Class<Message> getMessageClass() {
    return this.messageClass;
  }
  
  @Override
  public final Message createMessage() {
    if (this.messageClass == null) {
      throw new IllegalStateException("Message class not created");
    }

    try {
      return this.messageClass.newInstance();

    } catch (Exception ex) {
      logger.error(null, ex);

      throw new RuntimeException(ex);
    }
  }

}
