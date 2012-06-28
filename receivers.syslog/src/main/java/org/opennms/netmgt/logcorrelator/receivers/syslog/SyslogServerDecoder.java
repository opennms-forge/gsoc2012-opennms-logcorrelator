package org.opennms.netmgt.logcorrelator.receivers.syslog;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;
import org.opennms.netmgt.logcorrelator.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decodes a received message to a RFC5424 syslog message.
 *
 * The message to decode must be a {@link String} in the form specified by
 * RFC5424. The output message is a {@link Message} filled with the parsed
 * syslog message.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public class SyslogServerDecoder extends OneToOneDecoder {

	private static final Logger logger = LoggerFactory.getLogger(
			SyslogServerDecoder.class);

	private final SyslogParser parser;

	public SyslogServerDecoder(final SyslogParser parser) {
		this.parser = parser;
	}

	@Override
	protected final Object decode(final ChannelHandlerContext context,
								  final Channel channel, final Object message) throws Exception {
		if (!(message instanceof String)) {
			return message;
		}

		logger.info("Syslog message received: {}", message);

		return this.parser.parse((String) message);
	}
}
