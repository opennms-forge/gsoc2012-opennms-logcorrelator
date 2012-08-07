package org.opennms.logcorrelator.correlators.drools;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClassLoaderTest {
  private final static Logger logger = LoggerFactory.getLogger(ClassLoaderTest.class);

  private ClassLoader loader;

  private Class messageClazz;

  private byte[] messageBytes;


  private class TestClassLoader extends ClassLoader {
    public TestClassLoader(final ClassLoader parent) {
      super(parent);
    }

    @Override
    public Class<?> loadClass(final String name) throws ClassNotFoundException {
      logger.debug("loadClass({})", name);

      try {
        return this.getParent().loadClass(name);

      } catch (ClassNotFoundException ex) {
        logger.debug("loadClass({}) local", name);
        return this.loadClass(name, false);
      }
    }

    @Override
    public InputStream getResourceAsStream(String name) {
      logger.debug("getResourceAsStream({})", name);


      if (name.equals("org.opennms.logcorrelator.impl.MessageImpl.class") ||
          name.equals("org/opennms/logcorrelator/impl/MessageImpl.class")) {
        logger.debug("getResourceAsStream({}) byte stream", name);
        return new ByteArrayInputStream(ClassLoaderTest.this.messageBytes);
      }

      return super.getResourceAsStream(name);
    }
  }

  @Before
  public void setUp() throws Exception {
    ClassPool cp = new ClassPool(true);

    this.loader = new TestClassLoader(Message.class.getClassLoader());

    cp.makePackage(this.loader, "org.opennms.logcorrelator.impl");

    CtClass c = cp.makeClass("org.opennms.logcorrelator.impl.MessageImpl");
    c.addInterface(ClassPool.getDefault().get("org.opennms.logcorrelator.api.Message"));

    c.addField(CtField.make("private java.lang.String receiverId = null;", c));
    c.addMethod(CtMethod.make("public java.lang.String getReceiverId() { return this.receiverId; }", c));

    c.stopPruning(true);

    this.messageClazz = c.toClass(this.loader, null);
    this.messageBytes = c.toBytecode();
  }

  @After
  public void tearDown() throws Exception {
    this.loader = null;
  }

  @Test()
  public void testSystemClassLoaderBaseFound() throws Exception {
    Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("org.opennms.logcorrelator.api.Message");
    assertEquals(Message.class, clazz);
  }

  @Test(expected = ClassNotFoundException.class)
  public void testSystemClassLoaderImplNotFound() throws Exception {
    ClassLoader.getSystemClassLoader().loadClass("org.opennms.logcorrelator.impl.MessageImpl");
  }

  @Test()
  public void testContextClassLoaderBaseFound() throws Exception {
    Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("org.opennms.logcorrelator.api.Message");
    assertEquals(Message.class, clazz);
  }

  @Test(expected = ClassNotFoundException.class)
  public void testContextClassLoaderImplNotFound() throws Exception {
    Thread.currentThread().getContextClassLoader().loadClass("org.opennms.logcorrelator.impl.MessageImpl");
  }

  @Test()
  public void testCustomClassLoaderBaseFound() throws Exception {
    Class<?> clazz = this.loader.loadClass("org.opennms.logcorrelator.api.Message");

    assertEquals(Message.class, clazz);
  }

  @Test()
  public void testCustomClassLoaderImplFound() throws Exception {
    Class<?> clazz = this.loader.loadClass("org.opennms.logcorrelator.impl.MessageImpl");

    assertEquals(this.messageClazz, clazz);
    assertNotNull(this.messageClazz.getPackage());
    assertEquals(Message.class, clazz.getInterfaces()[0]);
    assertEquals(this.loader, clazz.getClassLoader());
  }

  @Test()
  public void testCustomClassLoaderImport() throws Exception {
    KnowledgeBuilderConfiguration builderConfiguration = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration(null,
                                                                                                                  this.loader);
    KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder(builderConfiguration);
    builder.add(ResourceFactory.newReaderResource(new StringReader("" +
                                                                   "package droolstest; \n" +
                                                                   "\n" +
                                                                   "import org.opennms.logcorrelator.impl.MessageImpl; \n" +
                                                                   "\n" +
                                                                   "dialect \"java\"\n" +
                                                                   "\n" +
                                                                   "\n" +
                                                                   "rule \"test\"\n" +
                                                                   "  no-loop\n" +
                                                                   "when\n" +
                                                                   "  $m : MessageImpl(receiverId == \"dummyReceiver\")\n" +
                                                                   "then\n" +
                                                                   "  System.out.println(\"Whäwhäwhäwhäwhäwhä: \" + $m);\n" +
                                                                   "end" +
                                                                   "")), ResourceType.DRL);

    for (KnowledgeBuilderError error : builder.getErrors()) {
      logger.error(error.getMessage());
    }

    assertFalse(builder.hasErrors());
  }

}
