package org.opennms.logcorrelator.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class MessageCodeClassLoader extends ClassLoader {
  private final static Logger logger = LoggerFactory.getLogger(MessageCodeClassLoader.class);

  private final ClassPool classPool;

  public MessageCodeClassLoader(final ClassPool classPool,
                                final ClassLoader parent) {
    super(parent);

    this.classPool = classPool;
  }

  @Override
  public Class<?> loadClass(String name) throws ClassNotFoundException {
    try {
      return this.getParent().loadClass(name);

    } catch (ClassNotFoundException ex) {
      return this.loadClass(name, false);
    }
  }

  @Override
  public InputStream getResourceAsStream(final String name) {
    // Convert the resource name to a class name
    final String className = name.replaceAll("\\.class$", "").replace("/", ".");
    
    // Get the class representation for the requested resource
    final CtClass clazz = this.classPool.getOrNull(className);
    
    // Use default resource if class is not generated
    if (clazz == null) {
      return super.getResourceAsStream(name);
    }
    
    // Get bytecode of class and return it as stream
    try {
      return new ByteArrayInputStream(clazz.toBytecode());
      
    } catch (Exception ex) {
      logger.error(null, ex);
      throw new RuntimeException(ex);
    }
  }
  
}
