package org.opennms.netmgt.logcorrelator.receivers.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.opennms.netmgt.logcorrelator.api.Receiver;

public abstract class NettyReceiver extends Receiver {
    private final ConnectionlessBootstrap bootstrap;

    private Channel channel;

    public NettyReceiver(final String host, final int port) {
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
    public final void start() {
	this.channel = this.bootstrap.bind();
    }

    @Override
    public final void stop() {
	this.channel.close();
    }

    protected abstract ChannelPipelineFactory createPipelineFactory();

}
