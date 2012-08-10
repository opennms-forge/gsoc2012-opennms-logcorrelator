package org.opennms.logcorrelator.core;

import java.io.InputStream;
import javassist.ClassPool;
import javassist.CtClass;
import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class MessageCodeClassLoaderTest {
  private ClassLoader parentLoader;

  private ClassPool classPool;

  private MessageCodeClassLoader loader;

  @Before
  public void setUp() {
    this.parentLoader = createMock(ClassLoader.class);
    this.classPool = createMock(ClassPool.class);

    this.loader = new MessageCodeClassLoader(this.classPool,
                                             this.parentLoader);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testChainedClassLoading() throws Exception {
    final String className = "my.test.Class";
    
    final Class clazz = Object.class;
    
    expect(this.parentLoader.loadClass(same(className))).andReturn(clazz);
    
    replay(this.classPool,
           this.parentLoader);
    
    assertSame(clazz, this.loader.loadClass(className));

    verify(this.classPool,
           this.parentLoader);
  }
  
  @Test
  public void testChainedResourceLoading() throws Exception {
    final String resourceName = "my/test/Class.class";
    final String className = "my.test.Class";
    
    expect(this.classPool.getOrNull(eq(className))).andReturn(null);
    expect(this.parentLoader.getResource(eq(resourceName))).andReturn(null);
    
    replay(this.classPool,
           this.parentLoader);
    
    assertNull(this.loader.getResourceAsStream(resourceName));

    verify(this.classPool,
           this.parentLoader);
  }

  @Test
  public void testPoolResourceLoading() throws Exception {
    final String resourceName = "my/test/Class.class";
    final String className = "my.test.Class";
    
    final byte[] bytecode = new byte[] {
      42,
      23
    };
    
    final CtClass clazz = createMock(CtClass.class);
    expect(clazz.toBytecode()).andReturn(bytecode);
    
    expect(this.classPool.getOrNull(eq(className))).andReturn(clazz);
    
    replay(this.classPool,
           this.parentLoader,
           clazz);
    
    final InputStream is = this.loader.getResourceAsStream(resourceName);
    assertEquals(42, is.read());
    assertEquals(23, is.read());

    verify(this.classPool,
           this.parentLoader,
           clazz);
  }

}
