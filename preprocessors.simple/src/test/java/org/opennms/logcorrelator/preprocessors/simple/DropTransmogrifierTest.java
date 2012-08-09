package org.opennms.logcorrelator.preprocessors.simple;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.Preprocessor;
import org.opennms.logcorrelator.api.Transmogrifier;


public class DropTransmogrifierTest extends TestCase {

  @Override
  protected void setUp() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {
  }

  @Test
  public void testDropping() throws Exception {
    final Transmogrifier.Context context = createMock(Transmogrifier.Context.class);
    
    final Message message = createMock(Message.class);

    final Transmogrifier transmogrifier = new DropTransmogrifier();

    replay(context, message);
    transmogrifier.transmogrify(context, message);
    verify(context, message);
  }

}
