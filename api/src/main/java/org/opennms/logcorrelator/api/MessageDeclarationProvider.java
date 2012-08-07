package org.opennms.logcorrelator.api;


/**
 * Provider for message declarations.
 *
 * Each user of the {@link Message} must provide field declarations to acquire a
 * {@link MessageAccessor} for the field to access. To declare fields, the
 * {@link #register(org.opennms.logcorrelator.api.MessageDeclarator)} method is
 * called on startup.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface MessageDeclarationProvider {
  /**
   * Register message fields.
   *
   * Registers all fields in use to acquire {@link MessageAccessor}s.
   *
   * @param declarator the message declarator
   */
  public abstract void registerMessageDeclaration(final MessageDeclarator declarator);

}
