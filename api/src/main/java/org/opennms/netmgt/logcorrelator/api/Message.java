package org.opennms.netmgt.logcorrelator.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface for a log message.
 *
 * The message consists of two pars: the header and the body.
 *
 * The header part of the message is a list of fields. Each field is
 * identified using a unique field name.
 *
 * The body part is the content of the message.
 *
 * @author Dustin Frisch <dustin.frisch@gmail.com>
 */
public interface Message extends Serializable {

	/**
	 * Returns the header of the message.
	 *
	 * @return the header
	 */
	public abstract Map<String, String> getHeaders();

	public abstract String getHeader(final String name);

	public abstract void setHeader(final String name, final String value);

	/**
	 * Returns the body of the log message.
	 *
	 * @return the body
	 */
	public abstract String getBody();

	public abstract void setBody(final String body);
}
