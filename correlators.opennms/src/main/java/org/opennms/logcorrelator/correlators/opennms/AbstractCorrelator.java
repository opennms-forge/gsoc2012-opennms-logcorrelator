package org.opennms.logcorrelator.correlators.opennms;

import java.net.UnknownHostException;
import org.opennms.logcorrelator.api.Correlator;
import org.opennms.logcorrelator.api.Message;
//import org.opennms.netmgt.model.events.EventProxy;
//import org.opennms.netmgt.model.events.EventProxyException;
//import org.opennms.netmgt.utils.TcpEventProxy;
//import org.opennms.netmgt.xml.event.Event;
//import org.opennms.netmgt.xml.event.Logmsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractCorrelator implements Correlator {
  private final static Logger logger = LoggerFactory.getLogger(AbstractCorrelator.class);

//  private EventProxy proxy;

  public AbstractCorrelator() {
//    try {
//      this.proxy = new TcpEventProxy();
//
//    } catch (UnknownHostException e) {
//      logger.error("Failed to create event proxy", e);
//    }
  }

  protected void sendEvent(final Message message) {
    logger.debug("Creating OpenNMS event for message: {}", message);

//    Event event = new Event();
//    event.setUei("uei.opennms.org/logcorrelation/message");
//
//    Logmsg logmsg = new Logmsg();
//    logmsg.setContent(message.toString());
//    event.setLogmsg(logmsg);
//
//    try {
//      logger.debug("Sending OpenNMS event: {}", event);
//
//      this.proxy.send(event);
//
//    } catch (EventProxyException e) {
//      logger.error("Failed to send event", e);
//    }
  }

}
