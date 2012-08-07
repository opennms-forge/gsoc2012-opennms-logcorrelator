package org.opennms.logcorrelator.core;

import java.util.HashMap;
import javassist.*;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;


/**
 * Code generator for {@link Message} and {@link MessageAccessor}
 * implementations.
 *
 * The code generator is used by the {@link MessageDeclaratorImpl} to generate a
 * {@link Message} implementation using the {@link AbstractMessage} with a
 * {@link HashMap} based implementation.
 *
 * While declaring message fields using the {@link MessageDeclaratorImpl} for
 * each declaration a {@link MessageAccessor} implementation will generated.
 * Additionally bean property getters and setters are added to the message
 * implementation.
 *
 * The generated {@link MessageAccessor} implementations will utilize the
 * generated getters and setters of the message implementation.
 *
 * The generated message implementation will look like the this:
 * <pre>
 * public class MessageImpl extends AbstractMessage {
 *   private final HashMap fields = new HashMap();
 *
 *   // Constructor ommited...
 *
 *   // Getter for declared field "foo"
 *   public String getFoo() {
 *     return (String) this.fields.get("foo");
 *   }
 *
 *   // Setter for declared field "foo"
 *   public void setFoo(String value) {
 *     this.fields.pet("foo", value);
 *   }
 *
 *   // Other fields...
 * }
 * </pre>
 *
 * And the accessor implementation will look like this:
 * <pre>
 * public class FooMessageAccessorImpl implements Message {
 *   // Constructor ommited...
 *
 *   public String get(Message message) {
 *     return ((MessageImpl) message).getFoo();
 *   }
 *
 *   public void set(Message message, String value) {
 *     ((MessageImpl) message).setFoo(value);
 *   }
 * }
 * </pre>
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public final class MessageCodeGenerator {
  private final static Logger logger = LoggerFactory.getLogger(MessageCodeGenerator.class);

  /**
   * The package of the message implementation class.
   */
  public static final String PACKAGE_NAME = "org.opennms.logcorrelator.impl";

  /**
   * The template group used for message class generation.
   */
  private final static STGroup messageTemplateGroup = new STGroupFile("templates/message.stg");

  /**
   * The template group used for accessor class generation.
   */
  private final static STGroup accessorTemplateGroup = new STGroupFile("templates/accessor.stg");

  /**
   * The class pool for the code generator.
   */
  private final ClassPool classPool;

  /**
   * The class loader holding the generated classes.
   */
  private final MessageCodeClassLoader loader;

  /**
   * The message implementation class definition.
   */
  private final CtClass messageClass;

  public MessageCodeGenerator() {
    // Create a class pool for the message implementation and the accessor implementations
    this.classPool = new ClassPool(true);

    // Create the class loader for the implementations.
    this.loader = new MessageCodeClassLoader(this.classPool,
                                             MessageCodeGenerator.class.getClassLoader());

    try {
      // Create package containing the message implementation class
      this.classPool.makePackage(this.loader, MessageCodeGenerator.PACKAGE_NAME);
      this.classPool.importPackage(MessageCodeGenerator.PACKAGE_NAME);

      // Define message implementation class
      this.messageClass = this.classPool.makeClass(MessageCodeGenerator.PACKAGE_NAME + "." + "MessageImpl");

      // Let class implement the message interface
      this.messageClass.setSuperclass(ClassPool.getDefault().get(AbstractMessage.class.getName()));

      // Define default constructor
      this.messageClass.addConstructor(CtNewConstructor.defaultConstructor(this.messageClass));

      // Define implementions field storage
      final ST stImplMap = messageTemplateGroup.getInstanceOf("fields");
      this.messageClass.addField(CtField.make(stImplMap.render(), this.messageClass));

    } catch (Exception ex) {
      logger.error("Failed to create code", ex);

      throw new RuntimeException(ex);
    }
  }

  /**
   * Returns the full qualified class name of message currently created by the
   * code generator.
   *
   * @return the class name
   */
  public String getMessageImplementationName() {
    return this.messageClass.getName();
  }

  /**
   * Creates a class implementing the message interface.
   *
   * The returned class implements {@link Message}, has a default constructor
   * and provides getter and setter methods for all declared fields.
   *
   * @return the generated class
   */
  public Class<Message> createMessageImplementation() {
    logger.debug("Creating message implementation class");

    try {

      // Create and return class
      return this.messageClass.toClass(this.loader,
                                       null);

    } catch (CannotCompileException ex) {
      logger.error("Failed to create code", ex);

      throw new RuntimeException(ex);
    }
  }

  /**
   * Creates a class implementing the accessor interface using the given field
   * declaration.
   *
   * The implemented class corresponds with the generated message class. For
   * each generated accessor, a field is added to the message implementation
   * which itself is used by the accessor implementation to access the data of
   * the message.
   *
   * @param <T> the type of the value to access
   * @param declaration the declaration used to declare the field and defining
   * the value to access for the generated accesor.
   *
   * @return the generated class
   */
  public <T> Class<MessageAccessor<T>> createAccessorImplementation(MessageFieldDeclaration<T> declaration) {
    logger.debug("Creating message accessor implementation class for field '{}' with type {}",
                 declaration.getName(),
                 declaration.getType());

    try {

      // Define getter on message class
      final ST stMessageGetter = messageTemplateGroup.getInstanceOf("getter");
      stMessageGetter.add("declaration", declaration);
      this.messageClass.addMethod(CtMethod.make(stMessageGetter.render(), this.messageClass));

      // Define setter on message class
      final ST stMessageSetter = messageTemplateGroup.getInstanceOf("setter");
      stMessageSetter.add("declaration", declaration);
      this.messageClass.addMethod(CtMethod.make(stMessageSetter.render(), this.messageClass));

      // Define accessor class for field
      final CtClass accessorClass = this.classPool.makeClass(MessageCodeGenerator.PACKAGE_NAME + "." + declaration.getAccessorClass());

      // Let class implement the message accessor interface
      accessorClass.addInterface(ClassPool.getDefault().get(MessageAccessor.class.getName()));

      // Define constructor
      final ST stAccessorConstructor = accessorTemplateGroup.getInstanceOf("constructor");
      stAccessorConstructor.add("declaration", declaration);
      accessorClass.addConstructor(CtNewConstructor.make(stAccessorConstructor.render(), accessorClass));

      // Define getFieldName
      final ST stAccessorNameGetter = accessorTemplateGroup.getInstanceOf("getFieldName");
      stAccessorNameGetter.add("declaration", declaration);
      accessorClass.addMethod(CtMethod.make(stAccessorNameGetter.render(), accessorClass));

      // Define getFieldType
      final ST stAccessorTypeSetter = accessorTemplateGroup.getInstanceOf("getFieldType");
      stAccessorTypeSetter.add("declaration", declaration);
      accessorClass.addMethod(CtMethod.make(stAccessorTypeSetter.render(), accessorClass));

      // Define getter
      final ST stAccessorGetter = accessorTemplateGroup.getInstanceOf("get");
      stAccessorGetter.add("declaration", declaration);
      accessorClass.addMethod(CtMethod.make(stAccessorGetter.render(), accessorClass));

      // Define setter
      final ST stAccessorSetter = accessorTemplateGroup.getInstanceOf("set");
      stAccessorSetter.add("declaration", declaration);
      accessorClass.addMethod(CtMethod.make(stAccessorSetter.render(), accessorClass));

      // Create and return class
      return accessorClass.toClass(this.loader,
                                   null);

    } catch (Exception ex) {
      logger.error("Failed to create code", ex);

      throw new RuntimeException(ex);
    }
  }

}
