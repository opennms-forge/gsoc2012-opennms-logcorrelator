package org.opennms.netmgt.logcorrelator.api;

import java.util.HashMap;
import java.util.Map;

public class SimpleMessage implements Message {

    private static final long serialVersionUID = -3026418862729289709L;

    private Map<String, String> headers;

    private String body;

    public SimpleMessage() {
	this.headers = new HashMap<String, String>();
    }

    @Override
    public Map<String, String> getHeaders() {
	return this.headers;
    }

    @Override
    public String getHeader(String name) {
	return this.headers.get(name);
    }

    @Override
    public void setHeader(String name, String value) {
	this.headers.put(name, value);
    }

    @Override
    public String getBody() {
	return this.body;
    }

    @Override
    public void setBody(String body) {
	this.body = body;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();

	sb.append("SimpleMessage[");

	for (String name : this.headers.keySet()) {
	    sb.append(name);
	    sb.append("=\"");
	    sb.append(this.headers.get(name));
	    sb.append("\"");
	    sb.append(", ");
	}

	sb.append(this.body);

	sb.append("]");

	return sb.toString();
    }
}
