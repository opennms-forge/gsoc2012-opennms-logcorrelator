package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import java.util.regex.Pattern;
import org.opennms.netmgt.logcorrelator.api.Message;
import org.opennms.netmgt.logcorrelator.api.Preprocessor;

public class RegexReplacePreprocessor extends Preprocessor {

	private Pattern namePattern;

	private Pattern valuePattern;

	private String valueReplacement;

	public RegexReplacePreprocessor(final String namePattern,
									final String valuePattern,
									final String valueReplacement) {
		this.namePattern = Pattern.compile(namePattern);
		this.valuePattern = Pattern.compile(valuePattern);
		this.valueReplacement = valueReplacement;
	}

	@Override
	public void process(Message message) {
		for (String name : message.getHeaders().keySet()) {
			if (this.namePattern.matcher(name).matches()) {
				String value = message.getHeader(name);

				value = valuePattern.matcher(value).replaceAll(
						valueReplacement);

				message.setHeader(name, value);
			}
		}

		this.pass(message);
	}
}
