package org.opennms.logcorrelator.core;

import org.junit.After;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;


public class MessageCodeGeneratorTest {
  private MessageCodeGenerator generator;

  @Before
  public void setUp() {
    this.generator = new MessageCodeGenerator();
  }

  @After
  public void tearDown() {
    this.generator = null;
  }

  @Test
  public void testMessageImplementationBaseClassLoader() throws Exception {
    Class<Message> clazz = this.generator.createMessageImplementation();

    // Generated message class must be loaded by own class loader, not the
    // system class loader
    assertNotSame(AbstractMessage.class.getClassLoader(), clazz.getClassLoader());

    // Superclass of generated message class must be loaded by the same class
    // loader as the known base class
    assertSame(AbstractMessage.class.getClassLoader(), clazz.getSuperclass().getClassLoader());
  }

  @Test
  public void testAccessorImplementationInterfaceClassLoader() throws Exception {
    Class<MessageAccessor<String>> clazz = this.generator.createAccessorImplementation(new MessageFieldDeclaration<String>(this.generator,
                                                                                                                        "foo",
                                                                                                                        String.class));

    // Generated message accessor class must be loaded by own class loader, not
    // the system class loader
    assertNotSame(AbstractMessage.class.getClassLoader(), clazz.getClassLoader());

    // Superclass of generated message accessor class must be loaded by the same
    // class loader as the known base class
    for (Class<?> interf : clazz.getInterfaces()) {
      assertSame(MessageAccessor.class.getClassLoader(), interf.getClassLoader());
    }
  }

}
