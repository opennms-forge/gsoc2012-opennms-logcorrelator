package org.opennms.logcorrelator.receivers.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.opennms.logcorrelator.api.Receiver;


public abstract class NettyReceiver implements Receiver {
  private final String host;

  private final int port;

  private ConnectionlessBootstrap bootstrap;

  private Channel channel;

  public NettyReceiver(final String host,
                       final int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void init() {

    final DatagramChannelFactory f = new NioDatagramChannelFactory(
            Executors.newCachedThreadPool());

    this.bootstrap = new ConnectionlessBootstrap(f);

    this.bootstrap.setOption("localAddress", new InetSocketAddress(host,
                                                                   port));
    this.bootstrap.setOption("reuseAddress", true);

    this.bootstrap.setOption("child.tcpNoDelay", true);

    this.bootstrap.setPipelineFactory(this.createPipelineFactory());
  }

  @Override
  public void destroy() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public final void start() {
    this.channel = this.bootstrap.bind();
  }

  @Override
  public final void stop() {
    this.channel.close();
  }

  protected abstract ChannelPipelineFactory createPipelineFactory();

}
