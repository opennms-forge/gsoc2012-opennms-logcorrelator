package org.opennms.logcorrelator.core;

import java.util.HashMap;
import java.util.Map;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;
import org.opennms.logcorrelator.api.MessageDeclarator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of the {@link MessageDeclarator}.
 *
 * The implementation uses the {@link MessageCodeGenerator} to generate the {@link Message}
 * and {@link MessageAccessor} implementations.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public class MessageDeclaratorImpl implements MessageDeclarator {
  private final static Logger logger = LoggerFactory.getLogger(MessageDeclaratorImpl.class);

  /**
   * The code generator used to create message implementation and accessor
   * implementations.
   */
  private final MessageCodeGenerator codeGenerator = new MessageCodeGenerator();

  /**
   * Map of declared field.
   */
  private final Map<String, MessageFieldDeclaration<?>> fields = new HashMap<String, MessageFieldDeclaration<?>>();

  private final Map<MessageFieldDeclaration<?>, MessageAccessor<?>> accessors = new HashMap<MessageFieldDeclaration<?>, MessageAccessor<?>>();

  public MessageDeclaratorImpl() {
  }

  @Override
  public <T> MessageAccessor<T> registerField(final String fieldName,
                                         final Class<T> fieldType) {
    // TODO: Check if we can unify the two maps and make the declaration a
    // temporary object
    
    // Check if field declaration with given name exist and try to cast to given
    // type - this will throw a ClasCastException if the types are not
    // compatible
    MessageFieldDeclaration<T> declaration = (MessageFieldDeclaration<T>) this.fields.get(fieldName);

    if (declaration == null) {
      // No existing declaration found - create a new one
      this.fields.put(fieldName, declaration = new MessageFieldDeclaration<T>(this.codeGenerator,
                                                                              fieldName,
                                                                              fieldType));
    } else if (!fieldType.isAssignableFrom(declaration.getType())) {
      throw new ClassCastException(String.format("Can not cast field '%1$s' to '%2$s'. Already declared as '%3$s'",
                                                 fieldName,
                                                 fieldType.getName(),
                                                 declaration.getType().getName()));
    }

    // Get message accessor for declaration
    MessageAccessor<T> accessor = (MessageAccessor<T>) this.accessors.get(declaration);

    // Create new message accessor if no accessor for this declaration exists
    if (accessor == null) {
      try {
        
        // Create and store message accessor for field
        accessor = this.codeGenerator.createAccessorImplementation(declaration).newInstance();
        this.accessors.put(declaration, accessor);


      } catch (Exception ex) {
        logger.error("Failed to instantiate message accessor", ex);

        throw new RuntimeException(ex);
      }
    }
    
    return accessor;
  }

  /**
   * Creates a class implementing the message interface.
   *
   * The returned class implements {@link Message}, has a default constructor
   * and provides getter and setter methods for all declared fields.
   *
   * @return the class
   */
  public Class<Message> createMessageImplementationClass() {
    return this.codeGenerator.createMessageImplementation();
  }

}
