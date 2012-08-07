package org.opennms.logcorrelator.api;

/**
 * Receiver for messages.
 * 
 * A receiver is the first step in the processing chain. Its implementation
 * receives messages using the implementation specific protocol and forward
 * these message to the processing chain.
 * 
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface Receiver extends MessageDeclarationProvider, Plugin {

}
