package org.opennms.logcorrelator.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractReceiver implements Receiver {
  private final static Logger logger = LoggerFactory.getLogger(AbstractReceiver.class);

  private final String id;

  private final MessageFactory messageFactory;

  private MessageAccessor<String> receiverIdAccessor;

  protected AbstractReceiver(final String id,
                             final MessageFactory messageFactory) {
    this.id = id;
    this.messageFactory = messageFactory;
  }

  @Override
  public final String getId() {
    return this.id;
  }

  public Message createMessage() {
    final Message message = this.messageFactory.create();
    message.set(this.receiverIdAccessor, this.id);

    return message;
  }

  @Override
  public void registerMessageDeclaration(MessageDeclarator declarator) {
    this.receiverIdAccessor = declarator.registerField("receiverId", String.class);
  }

}
