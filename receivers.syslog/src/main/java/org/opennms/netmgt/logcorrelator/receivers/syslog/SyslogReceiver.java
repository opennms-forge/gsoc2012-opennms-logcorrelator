package org.opennms.netmgt.logcorrelator.receivers.syslog;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.opennms.netmgt.logcorrelator.api.Receiver;

public abstract class SyslogReceiver extends Receiver {

	private final SyslogParser parser = this.createSyslogParser();

	private final ConnectionlessBootstrap bootstrap;

	private Channel channel;

	public SyslogReceiver() {
		final DatagramChannelFactory datagramChannelFactory = new NioDatagramChannelFactory(
				Executors.newCachedThreadPool());

		this.bootstrap = new ConnectionlessBootstrap(datagramChannelFactory);
		this.bootstrap.setPipelineFactory(
				new SyslogServerPipelineFactory(this.parser));
	}

	@Override
	public final void start() {
		this.channel = this.bootstrap.bind(new InetSocketAddress(10514));
	}

	@Override
	public final void stop() {
		this.channel.close();

	}

	protected abstract SyslogParser createSyslogParser();
}
