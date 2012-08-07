package org.opennms.logcorrelator.api;


/**
 * A message correlator.
 *
 * The correlator is the last step in the message processing chain.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public interface Correlator extends Processor, MessageDeclarationProvider, Plugin {
}
