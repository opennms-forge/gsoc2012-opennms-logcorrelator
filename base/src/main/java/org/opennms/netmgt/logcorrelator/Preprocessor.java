package org.opennms.netmgt.logcorrelator;

/**
 * Base class for all preprocessors.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public abstract class Preprocessor extends Processor {

	/**
	 * Next processor in the chain.
	 */
	private Processor nextProcessor = null;

	public Preprocessor() {
	}

	/**
	 * Returns the next processor in the chain.
	 *
	 * @return the processor
	 */
	public final Processor getNextProcessor() {
		return this.nextProcessor;
	}

	/**
	 * Sets the next processor in the chain.
	 *
	 * @param nextProcessor the next processor
	 */
	public final void setNextProcessor(final Processor nextProcessor) {
		this.nextProcessor = nextProcessor;
	}

	/**
	 * Relays the message to the next processor in the chain.
	 *
	 * @param message the message to relay
	 */
	protected final void pass(final Message message) {
		this.nextProcessor.process(message);
	}
}
