package org.opennms.logcorrelator.receivers.flume;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Responder;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.flume.source.avro.AvroFlumeEvent;
import org.apache.flume.source.avro.AvroSourceProtocol;
import org.apache.flume.source.avro.Status;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.opennms.logcorrelator.api.AbstractReceiver;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.api.Pipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlumeReceiver extends AbstractReceiver implements AvroSourceProtocol {
  private static final Logger logger = LoggerFactory.getLogger(FlumeReceiver.class);

  private final String host;

  private final int port;

  private Responder responder;

  private Server server;

  public MessageAccessor<String> BODY;

  public MessageAccessor<FlumeHeaders> HEADERS;

  public FlumeReceiver(final String id,
                       final MessageFactory messageFactory,
                       final Pipeline pipeline,
                       final String host,
                       final int port) {
    super(id,
          messageFactory,
          pipeline);

    this.host = host;
    this.port = port;
  }

  @Override
  public void registerMessageDeclaration(final MessageDeclarator declarator) {
    super.registerMessageDeclaration(declarator);

    this.BODY = declarator.registerField("body", String.class);
    this.HEADERS = declarator.registerField("headers", FlumeHeaders.class);
  }

  @Override
  public void init() {
    this.responder = new SpecificResponder(AvroSourceProtocol.class, this);

    this.server = new NettyServer(responder,
                                  new InetSocketAddress(this.host,
                                                        this.port),
                                  new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                                                                    Executors.newCachedThreadPool()));
  }

  @Override
  public void destroy() {
    try {
      this.server.join();

    } catch (InterruptedException ex) {
    }
  }

  @Override
  public void start() {
    this.server.start();
  }

  @Override
  public void stop() {
    this.server.close();
  }

  @Override
  public final Status append(AvroFlumeEvent event) throws AvroRemoteException {
    try {
      final Message message = this.createMessage(event);
      logger.debug("Message received from flume: {}", message);
      
      this.getPipeline().process(message);

    } catch (Exception ex) {
      logger.error("Filed to process message", ex);

      return Status.FAILED;
    }

    return Status.OK;
  }

  @Override
  public final Status appendBatch(List<AvroFlumeEvent> events) throws AvroRemoteException {
    try {
      for (final AvroFlumeEvent event : events) {
        final Message message = this.createMessage(event);
        logger.debug("Message received from flume: {}", message);
        
        this.getPipeline().process(message);
      }

    } catch (Exception ex) {
      logger.error("Filed to process message", ex);

      return Status.FAILED;
    }

    return Status.OK;
  }

  protected Message createMessage(final AvroFlumeEvent event) {
    final Message message = this.createMessage();

    final String body = new String(event.getBody().array());
    message.set(this.BODY, body);
    
    final FlumeHeaders headers = new FlumeHeaders(event.getHeaders());
    message.set(this.HEADERS, headers);
    
    return message;
  }

}
