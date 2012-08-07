package org.opennms.logcorrelator.receivers.syslog.rfc5424;

import java.util.Calendar;
import java.util.Map;
import org.easymock.Capture;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageFactory;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageFacility;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageSeverity;
import org.opennms.logcorrelator.receivers.syslog.SyslogParser;


public class Rfc5424SyslogParserTest {
  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testParser() throws Exception {
    final Rfc5424SyslogReceiver receiverMock = createMock(Rfc5424SyslogReceiver.class);
    receiverMock.APPLICATION = createMock(MessageAccessor.class);
    receiverMock.BODY = createMock(MessageAccessor.class);
    receiverMock.FACILITY = createMock(MessageAccessor.class);
    receiverMock.HOST = createMock(MessageAccessor.class);
    receiverMock.MESSAGE_ID = createMock(MessageAccessor.class);
    receiverMock.PROCESS_ID = createMock(MessageAccessor.class);
    receiverMock.PROTOCOLL_VERSION = createMock(MessageAccessor.class);
    receiverMock.SEVERITY = createMock(MessageAccessor.class);
    receiverMock.STRUCTURED_DATA = createMock(MessageAccessor.class);
    receiverMock.TIMESTAMP = createMock(MessageAccessor.class);

    final MessageFactory messageFactoryMock = createMock(MessageFactory.class);
    final Message messageMock = createMock(Message.class);

    expect(receiverMock.getMessageFactory()).andReturn(messageFactoryMock);
    expect(messageFactoryMock.createMessage()).andReturn(messageMock);

    messageMock.set(receiverMock.FACILITY, SyslogMessageFacility.USER);
    messageMock.set(receiverMock.SEVERITY, SyslogMessageSeverity.NOTICE);
    messageMock.set(receiverMock.PROTOCOLL_VERSION, 1);

    Capture<Calendar> timestamp = new Capture<Calendar>();
    messageMock.set(same(receiverMock.TIMESTAMP),
                    capture(timestamp));

    messageMock.set(receiverMock.HOST, "test.opennms.org");
    messageMock.set(receiverMock.APPLICATION, "ulf");
    messageMock.set(receiverMock.PROCESS_ID, "opennms");
    messageMock.set(receiverMock.MESSAGE_ID, "42");

    Capture<Map<String, Map<String, String>>> structuredData = new Capture<Map<String, Map<String, String>>>();
    messageMock.set(same(receiverMock.STRUCTURED_DATA),
                    capture(structuredData));

    messageMock.set(receiverMock.BODY, "OpenNMS is fubar");

    replay(receiverMock,
           messageFactoryMock,
           messageMock);

    final Rfc5424SyslogParser parser = new Rfc5424SyslogParser(receiverMock);

    parser.parse("<13>1 2012-06-25T05:56:03+02:00 test.opennms.org ulf opennms 42 [foo a=\"23\" b=\"42\" c=\"1337\"][bar x=\"ulf=[\\\"rock\\\\z\\\"\\]\"] OpenNMS is fubar");

    verify(receiverMock,
           messageFactoryMock,
           messageMock);

    assertEquals(2012, timestamp.getValue().get(Calendar.YEAR));
    assertEquals(Calendar.JUNE, timestamp.getValue().get(Calendar.MONTH));
    assertEquals(25, timestamp.getValue().get(Calendar.DAY_OF_MONTH));
    assertEquals(5, timestamp.getValue().get(Calendar.HOUR_OF_DAY));
    assertEquals(56, timestamp.getValue().get(Calendar.MINUTE));
    assertEquals(3, timestamp.getValue().get(Calendar.SECOND));
    assertEquals(0, timestamp.getValue().get(Calendar.MILLISECOND));
    assertEquals(2 * 60 * 60 * 1000, timestamp.getValue().get(Calendar.ZONE_OFFSET));
    
    assertNotNull(structuredData.getValue().get("foo"));
    assertEquals("23", structuredData.getValue().get("foo").get("a"));
    assertEquals("42", structuredData.getValue().get("foo").get("b"));
    assertEquals("1337", structuredData.getValue().get("foo").get("c"));
    
    assertNotNull(structuredData.getValue().get("bar"));
    assertEquals("ulf=[\"rock\\z\"]", structuredData.getValue().get("bar").get("x"));
  }

//
//  @Test
//  public void testStructuredData() throws Exception {
////    final String sd1 = message.get(sdAccessor1);
////    assertEquals("23", sd1);
////
////    final String sd2 = message.get(sdAccessor2);
////    assertEquals("42", sd2);
////
////    final String sd3 = message.get(sdAccessor3);
////    assertEquals("1337", sd3);
////
////    final String sd4 = message.get(sdAccessor4);
////    assertEquals("ulf=[\"rock\\z\"]", sd4);
//
//    fail();
//  }
}
