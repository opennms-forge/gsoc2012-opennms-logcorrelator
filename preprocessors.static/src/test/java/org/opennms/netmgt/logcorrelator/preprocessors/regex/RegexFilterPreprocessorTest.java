package org.opennms.netmgt.logcorrelator.preprocessors.regex;

import junit.framework.TestCase;
import org.junit.Test;

import static org.easymock.EasyMock.*;

import org.opennms.netmgt.logcorrelator.Preprocessor;
import org.opennms.netmgt.logcorrelator.Message;
import org.opennms.netmgt.logcorrelator.SimpleMessage;


public class RegexFilterPreprocessorTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
	}

	@Test
	public void testSinglePass() throws Exception {
		Message message = new SimpleMessage();
		message.setHeader("foo", "bar");
		message.setHeader("abc", "123");

		Preprocessor nextPreprocessorMock = createMock(Preprocessor.class);
		nextPreprocessorMock.process(message);

		RegexFilterPreprocessor preprocessor = new RegexFilterPreprocessor("^foo$", "^bar$");
		preprocessor.setNextProcessor(nextPreprocessorMock);

		replay(nextPreprocessorMock);

		preprocessor.process(message);
	}

	@Test
	public void testSingleDismiss() throws Exception {
		Message message = new SimpleMessage();
		message.setHeader("foo", "xxx");
		message.setHeader("abc", "123");

		Preprocessor nextPreprocessorMock = createMock(Preprocessor.class);

		RegexFilterPreprocessor preprocessor = new RegexFilterPreprocessor("^foo$", "^bar$");
		preprocessor.setNextProcessor(nextPreprocessorMock);

		replay(nextPreprocessorMock);

		preprocessor.process(message);
	}

	@Test
	public void testMultiplePass() throws Exception {
		Message message = new SimpleMessage();
		message.setHeader("foo.23", "bar");
		message.setHeader("foo.42", "baz");
		message.setHeader("abc", "123");

		Preprocessor nextPreprocessorMock = createMock(Preprocessor.class);
		nextPreprocessorMock.process(message);

		RegexFilterPreprocessor preprocessor = new RegexFilterPreprocessor("^foo\\..*$", "^ba[rz]$");
		preprocessor.setNextProcessor(nextPreprocessorMock);

		replay(nextPreprocessorMock);

		preprocessor.process(message);
	}

	@Test
	public void testMultipleDismiss() throws Exception {
		Message message = new SimpleMessage();
		message.setHeader("foo.23", "bar");
		message.setHeader("foo.42", "bax");
		message.setHeader("abc", "123");

		Preprocessor nextPreprocessorMock = createMock(Preprocessor.class);

		RegexFilterPreprocessor preprocessor = new RegexFilterPreprocessor("^foo\\..*$", "^ba[rz]$");
		preprocessor.setNextProcessor(nextPreprocessorMock);

		replay(nextPreprocessorMock);

		preprocessor.process(message);
	}
}
