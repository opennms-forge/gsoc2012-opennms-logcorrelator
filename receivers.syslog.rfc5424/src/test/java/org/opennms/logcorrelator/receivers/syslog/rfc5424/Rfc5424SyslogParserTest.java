package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import java.util.Calendar;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageFacility;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageSeverity;
import org.opennms.logcorrelator.receivers.syslog.SyslogParser;
import static org.easymock.EasyMock.*;


public class Rfc5424SyslogParserTest {
  private SyslogParser parser;

  private Message message;

  @Before
  public void setUp() throws Exception {
    Rfc5424SyslogReceiver receiverMock = createMock(Rfc5424SyslogReceiver.class);

    this.parser = new Rfc5424SyslogParser(receiverMock);

    this.message = parser.parse("<13>1 2012-06-25T05:56:03+02:00 test.opennms.org ulf opennms 42 [foo a=\"23\" b=\"42\" c=\"1337\"][bar x=\"ulf=[\\\"rock\\\\z\\\"\\]\"] OpenNMS is fubar");
  }

  @After
  public void tearDown() throws Exception {
    this.message = null;
    this.parser = null;
  }

  @Test
  public void testFacility() throws Exception {
//    final SyslogMessageFacility facility = message.get(this.receiver.FACILITY);
//
//    assertEquals(SyslogMessageFacility.USER, facility);
  }

  @Test
  public void testSeverity() throws Exception {
//    final SyslogMessageSeverity severity = message.get(this.receiver.SEVERITY);
//
//    assertEquals(SyslogMessageSeverity.NOTICE, severity);

  }

  @Test
  public void testProtocolVersion() throws Exception {
//    final int protocolVersion = message.get(this.receiver.PROTOCOLL_VERSION);
//
//    assertEquals(1, protocolVersion);
  }

  @Test
  public void testTimestamp() throws Exception {
//    final Calendar timestamp = message.get(this.receiver.TIMESTAMP);
//
//    assertEquals(2012, timestamp.get(Calendar.YEAR));
//    assertEquals(Calendar.JUNE, timestamp.get(Calendar.MONTH));
//    assertEquals(25, timestamp.get(Calendar.DAY_OF_MONTH));
//    assertEquals(5, timestamp.get(Calendar.HOUR_OF_DAY));
//    assertEquals(56, timestamp.get(Calendar.MINUTE));
//    assertEquals(3, timestamp.get(Calendar.SECOND));
//    assertEquals(0, timestamp.get(Calendar.MILLISECOND));
//    assertEquals(2 * 60 * 60 * 1000, timestamp.get(Calendar.ZONE_OFFSET));
  }

  @Test
  public void testHost() throws Exception {
//    final String host = message.get(this.receiver.HOST);
//
//    assertEquals("test.opennms.org", host);

  }

  @Test
  public void testApplication() throws Exception {
//    final String application = message.get(this.receiver.APPLICATION);
//
//    assertEquals("ulf", application);

  }

  @Test
  public void testProcessId() throws Exception {
//    final String processId = message.get(this.receiver.PROCESS_ID);
//
//    assertEquals("opennms", processId);

  }

  @Test
  public void testMessageId() throws Exception {
//    final String messageId = message.get(this.receiver.MESSAGE_ID);
//
//    assertEquals("42", messageId);

  }

  @Test
  public void testStructuredData() throws Exception {
//    final String sd1 = message.get(sdAccessor1);
//    assertEquals("23", sd1);
//
//    final String sd2 = message.get(sdAccessor2);
//    assertEquals("42", sd2);
//
//    final String sd3 = message.get(sdAccessor3);
//    assertEquals("1337", sd3);
//
//    final String sd4 = message.get(sdAccessor4);
//    assertEquals("ulf=[\"rock\\z\"]", sd4);

    fail();
  }

  @Test
  public void testBody() throws Exception {
//    final String body = message.get(this.receiver.BODY);
//
//    assertEquals("OpenNMS is fubar", body);
  }

}
