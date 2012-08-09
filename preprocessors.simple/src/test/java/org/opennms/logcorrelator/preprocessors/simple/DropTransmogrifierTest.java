package org.opennms.logcorrelator.preprocessors.simple;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.Preprocessor;
import org.opennms.logcorrelator.api.Transmogrifier;


public class DropPreprocessorTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {
  }

  @Test
  public void testDropping() throws Exception {
    final Preprocessor preprocessor = createMock(Preprocessor.class);
    
    final Message message = createMock(Message.class);

    final Transmogrifier transmogrifier = new DropTransmogrifier();

    replay(preprocessor, message);
    transmogrifier.transmogrify(preprocessor, message);
    verify(preprocessor, message);
  }

}
