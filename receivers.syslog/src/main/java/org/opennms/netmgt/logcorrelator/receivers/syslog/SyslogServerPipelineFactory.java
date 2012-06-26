package org.opennms.netmgt.logcorrelator.receivers.syslog;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.opennms.netmgt.logcorrelator.receivers.netty.MessageProcessorServerHandler;

public class SyslogServerPipelineFactory implements ChannelPipelineFactory {

	private final SyslogParser parser;

	public SyslogServerPipelineFactory(final SyslogParser parser) {
		this.parser = parser;
	}

	@Override
	public final ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("parser", new SyslogServerDecoder(this.parser));

		pipeline.addLast("handler", new MessageProcessorServerHandler());

		return pipeline;
	}
}
