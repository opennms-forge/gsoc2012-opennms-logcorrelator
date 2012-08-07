package org.opennms.logcorrelator.core;

import org.opennms.logcorrelator.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation if the pipeline interface.
 *
 * The pipeline can be populated on runtime using the
 * {@link #append(org.opennms.logcorrelator.api.Processor)} method.
 *
 * For each appended processor, the prior processor will be updated. For this,
 * all processors in the pipeline must be {@link PipelineProcessor}s.
 *
 * @author Dustin Frisch <fooker@lab.sh>
 */
public class PipelineImpl implements Pipeline {
  private final static Logger logger = LoggerFactory.getLogger(PipelineImpl.class);

  /**
   * The first processor in the pipeline.
   */
  private Processor firstProcessor;

  /**
   * The last processor in the pipeline.
   */
  private Processor lastProcessor;

  public PipelineImpl() {
  }

  @Override
  public final String getId() {
    return this.firstProcessor.getId();
  }

  @Override
  public final void process(Message message) {
    this.firstProcessor.process(message);
  }

  /**
   * Append the given preprocessor to the pipeline.
   *
   * @param preprocessor the preprocessor to append
   */
  public final void append(final Processor processor) {
    if (this.firstProcessor == null) {
      this.firstProcessor = processor;
      this.lastProcessor = processor;

    } else {
      if (!(this.lastProcessor instanceof PipelineProcessor)) {
        throw new IllegalStateException("Last processor in pipeline does not forward messages");
      }

      ((PipelineProcessor) this.lastProcessor).setNextProcessor(processor);

      this.lastProcessor = processor;
    }
  }

}
