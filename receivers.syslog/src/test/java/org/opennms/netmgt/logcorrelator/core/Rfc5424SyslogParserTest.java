package org.opennms.netmgt.logcorrelator.core;

import java.util.Calendar;
import junit.framework.TestCase;
import org.junit.Test;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogParser;
import org.opennms.netmgt.logcorrelator.receivers.syslog.SyslogParser;

public class Rfc5424SyslogParserTest extends TestCase {

	private Rfc5424SyslogParser parser;

	@Override
	protected void setUp() throws Exception {
		this.parser = new Rfc5424SyslogParser();
	}

	@Override
	protected void tearDown() throws Exception {
		this.parser = null;
	}

	@Test
	public void testFullMessage() throws Exception {
		final Message message = parser.parse(
				"<13>1 2012-06-25T05:56:03+02:00 test.opennms.org ulf opennms 42 [foo a=\"23\" b=\"42\" c=\"1337\"][bar x=\"ulf=[\\\"rock\\\\z\\\"\\]\"] OpenNMS is fubar");

		final String facility = message.getHeader(SyslogParser.HEADER_FACILITY);
		assertEquals("USER", facility);


		final String severity = message.getHeader(SyslogParser.HEADER_SEVERITY);
		assertEquals("NOTICE", severity);


		final String timestamp = message.getHeader(
				SyslogParser.HEADER_TIMESTAMP);
		assertEquals("2012-06-25 05:56:03.000+0200", timestamp);


		final String host = message.getHeader(
				SyslogParser.HEADER_HOST);
		assertEquals("test.opennms.org", host);


		final String application = message.getHeader(
				Rfc5424SyslogParser.HEADER_APPLICATION);
		assertEquals("ulf", application);


		final String processId = message.getHeader(
				Rfc5424SyslogParser.HEADER_PROCESS_ID);
		assertEquals("opennms", processId);


		final String messageId = message.getHeader(
				Rfc5424SyslogParser.HEADER_MESSAGE_ID);
		assertEquals("42", messageId);


		assertEquals("23", message.getHeader("foo.a"));
		assertEquals("42", message.getHeader("foo.b"));
		assertEquals("1337", message.getHeader("foo.c"));

		assertEquals("ulf=[\"rock\\z\"]", message.getHeader("bar.x"));


		final String body = message.getBody();
		assertEquals("OpenNMS is fubar", body);
	}
}
