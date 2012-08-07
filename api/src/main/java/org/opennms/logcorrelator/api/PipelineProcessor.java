package org.opennms.logcorrelator.api;


public interface PipelineProcessor extends Processor {

  /**
   * Returns the next processor in the chain.
   *
   * @return the processor
   */
  public abstract Processor getNextProcessor();

  /**
   * Sets the next processor in the chain.
   *
   * @param nextProcessor the next processor
   */
  public abstract void setNextProcessor(final Processor nextProcessor);
}
