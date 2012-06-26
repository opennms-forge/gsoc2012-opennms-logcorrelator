package org.opennms.netmgt.logcorrelator;

import java.util.HashMap;
import java.util.Map;

public class SimpleMessage implements Message {

	private Map<String, Object> headers;

	private String body;

	public SimpleMessage() {
		this.headers = new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> getHeaders() {
		return this.headers;
	}

	@Override
	public Object getHeader(String name) {
		return this.headers.get(name);
	}

	@Override
	public void setHeader(String name, Object value) {
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
}
