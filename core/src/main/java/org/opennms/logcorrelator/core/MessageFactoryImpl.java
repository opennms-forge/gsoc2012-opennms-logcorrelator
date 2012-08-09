package org.opennms.logcorrelator.core;

import java.lang.reflect.Constructor;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageDeclarationProvider;
import org.opennms.logcorrelator.api.MessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageFactoryImpl implements MessageFactory {
  private final static Logger logger = LoggerFactory.getLogger(MessageFactoryImpl.class);

  private final MessageDeclaratorImpl declarator;

  private Class<Message> messageClass;

  private Constructor<Message> messageDefaultConstructor;

  private Constructor<Message> messageCopyConstructor;

  public MessageFactoryImpl() {
    this.declarator = new MessageDeclaratorImpl();
  }

  public final void register(final MessageDeclarationProvider declarationProvider) {
    assert this.messageClass == null : "Message class already created";

    declarationProvider.registerMessageDeclaration(this.declarator);
  }

  public final void produce() {
    assert this.messageClass == null : "Message class already created";

    this.messageClass = this.declarator.createMessageImplementationClass();

    try {
      this.messageDefaultConstructor = this.messageClass.getConstructor(new Class<?>[]{});
      this.messageCopyConstructor = this.messageClass.getConstructor(new Class<?>[]{this.messageClass});

    } catch (Exception ex) {
      logger.error(null, ex);
      throw new RuntimeException(ex);
    }
  }

  @Override
  public Class<Message> getMessageClass() {
    return this.messageClass;
  }

  @Override
  public final Message create() {
    assert this.messageClass != null : "Message class not created";

    try {
      return this.messageDefaultConstructor.newInstance();

    } catch (Exception ex) {
      logger.error(null, ex);

      throw new RuntimeException(ex);
    }
  }

  @Override
  public final Message copy(final Message message) {
    assert this.messageClass != null : "Message class not created";
    assert message.getClass() == this.messageClass : "Message class mix-up - happy debuging!";

    try {
      return this.messageCopyConstructor.newInstance(message);

    } catch (Exception ex) {
      logger.error(null, ex);

      throw new RuntimeException(ex);
    }
  }

}
