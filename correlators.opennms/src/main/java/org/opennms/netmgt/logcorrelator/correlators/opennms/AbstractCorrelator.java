package org.opennms.netmgt.logcorrelator.correlators.opennms;

import java.net.UnknownHostException;

import org.apache.log4j.lf5.viewer.LogFactor5Dialog;
import org.opennms.netmgt.logcorrelator.api.Correlator;
import org.opennms.netmgt.logcorrelator.api.Message;
import org.opennms.netmgt.model.events.EventProxy;
import org.opennms.netmgt.model.events.EventProxyException;
import org.opennms.netmgt.utils.TcpEventProxy;
import org.opennms.netmgt.xml.event.Event;
import org.opennms.netmgt.xml.event.Logmsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCorrelator extends Correlator {

    private final static Logger logger = LoggerFactory
	    .getLogger(AbstractCorrelator.class);

    private EventProxy proxy;

    public AbstractCorrelator() {
	try {
	    this.proxy = new TcpEventProxy();

	} catch (UnknownHostException e) {
	    logger.error("Failed to create event proxy", e);
	}
    }

    protected void sendEvent(final Message message) {
	Event event = new Event();
	event.setUei("uei.opennms.org/logcorrelation/message");
	
	Logmsg logmsg = new Logmsg();
	logmsg.setContent(message.getBody());
	event.setLogmsg(logmsg);
	
	try {
	    this.proxy.send(event);
	    
	} catch (EventProxyException e) {
	    logger.error("Failed to send event", e);
	}
    }

}
