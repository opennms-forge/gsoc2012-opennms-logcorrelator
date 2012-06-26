package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import junit.framework.TestCase;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.Preprocessor;
import org.opennms.netmgt.logcorrelator.SimpleMessage;


public class RegexReplacePreprocessorTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	@Test
	public void testSingle() throws Exception {
		Message message = new SimpleMessage();
		message.setHeader("foo", "bar");
		message.setHeader("abc", "123");

		Preprocessor nextPreprocessorMock = createMock(Preprocessor.class);
		nextPreprocessorMock.process(message);

		RegexReplacePreprocessor preprocessor = new RegexReplacePreprocessor("^foo$", "^(ba)r$", "$1z");
		preprocessor.setNextProcessor(nextPreprocessorMock);

		replay(nextPreprocessorMock);

		preprocessor.process(message);
	}
}
