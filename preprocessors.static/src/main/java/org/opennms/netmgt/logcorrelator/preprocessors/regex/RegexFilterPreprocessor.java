package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import java.util.Map;
import java.util.regex.Pattern;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.Preprocessor;

public class RegexFilterPreprocessor extends Preprocessor {

	private Pattern namePattern;

	private Pattern valuePattern;

	public RegexFilterPreprocessor(final String namePattern,
								   final String valuePattern) {
		this.namePattern = Pattern.compile(namePattern);
		this.valuePattern = Pattern.compile(valuePattern);
	}

	@Override
	public void process(Message message) {
		for (Map.Entry<String, String> e : message.getHeaders().entrySet()) {
			if (this.namePattern.matcher(e.getKey()).matches() &&
				!this.valuePattern.matcher(e.getValue()).matches()) {
				return;
			}
		}

		this.pass(message);
	}
}
