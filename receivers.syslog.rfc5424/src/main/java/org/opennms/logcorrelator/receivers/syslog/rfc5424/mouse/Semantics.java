package org.opennms.logcorrelator.receivers.syslog.rfc5424.mouse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import mouse.runtime.SemanticsBase;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageFacility;
import org.opennms.logcorrelator.receivers.syslog.SyslogMessageSeverity;
import org.opennms.logcorrelator.receivers.syslog.rfc5424.Rfc5424SyslogReceiver;


public class Semantics extends SemanticsBase {
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");

  public Rfc5424SyslogReceiver receiver;

  public Message message;

  boolean pri() {
    final int pri = Integer.parseInt(rhsText(0, rhsSize()));

    this.message.set(this.receiver.FACILITY,
                     SyslogMessageFacility.values()[pri / 8]);
    this.message.set(this.receiver.SEVERITY,
                     SyslogMessageSeverity.values()[pri % 8]);

    return true;
  }

  boolean version() {
    final int version = Integer.parseInt(rhsText(0, rhsSize()));

    this.message.set(this.receiver.PROTOCOLL_VERSION,
                     version);

    return true;
  }

  boolean timestamp() {
    final String s = lhs().text();

    try {
      final Calendar timestamp = DatatypeFactory.newInstance().newXMLGregorianCalendar(s).toGregorianCalendar();

      this.message.set(this.receiver.TIMESTAMP,
                       timestamp);

    } catch (DatatypeConfigurationException ex) {
      return false;
    }

    return true;
  }

  boolean hostname() {
    final String host = rhsText(0, rhsSize());

    this.message.set(this.receiver.HOST,
                     host);

    return true;
  }

  boolean application() {
    final String application = rhsText(0, rhsSize());

    this.message.set(this.receiver.APPLICATION,
                     application);

    return true;
  }

  boolean processId() {
    final String processId = rhsText(0, rhsSize());

    this.message.set(this.receiver.PROCESS_ID,
                     processId);

    return true;
  }

  boolean messageId() {
    final String messageId = rhsText(0, rhsSize());

    this.message.set(this.receiver.MESSAGE_ID,
                     messageId);

    return true;
  }

  final Map<String, Map<String, String>> structuredData = new HashMap<String, Map<String, String>>();

  boolean structuredData() {

    this.message.set(this.receiver.STRUCTURED_DATA,
                     this.structuredData);

    return true;
  }

  boolean structuredDataElement() {
    final String base = rhs(1).text();

    Map<String, String> structuredElement = new HashMap<String, String>();
    this.structuredData.put(base,
                            structuredElement);

    for (int i = 3; i < rhsSize(); i += 5) {
      final String key = rhs(i + 0).text();
      final String value = (String) rhs(i + 2).get();

      structuredElement.put(key, value);
    }

    return true;
  }

  boolean structuredDataParamValue() {
    String text = rhsText(0, rhsSize());

    text = text.replace("\\\"", "\"");
    text = text.replace("\\\\", "\\");
    text = text.replace("\\]", "]");

    lhs().put(text);

    return true;
  }

  boolean body() {
    final String body = rhsText(0, rhsSize());

    this.message.set(this.receiver.BODY,
                     body);

    return true;
  }

}
