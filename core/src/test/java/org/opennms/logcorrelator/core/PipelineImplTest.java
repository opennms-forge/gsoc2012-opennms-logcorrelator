package org.opennms.logcorrelator.core;

import static org.easymock.EasyMock.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.PipelineProcessor;
import org.opennms.logcorrelator.api.Processor;


public class PipelineImplTest {
  private PipelineImpl pipeline;

  @Before
  public void setUp() {
    this.pipeline = new PipelineImpl();
  }

  @After
  public void tearDown() {
    this.pipeline = null;
  }

  @Test
  public void testChaining() throws Exception {
    final PipelineProcessor p1 = createMock(PipelineProcessor.class);
    final PipelineProcessor p2 = createMock(PipelineProcessor.class);

    p1.setNextProcessor(p2);

    replay(p1, p2);

    this.pipeline.append(p1);
    this.pipeline.append(p2);

    verify(p1, p2);
  }

  @Test(expected = IllegalStateException.class)
  public void testAppendAfterNonPipelineProcessor() throws Exception {
    final Processor p1 = createMock(Processor.class);
    final Processor p2 = createMock(Processor.class);

    this.pipeline.append(p1);
    this.pipeline.append(p2);
  }

  @Test
  public void testProcessing() throws Exception {
    final Processor p1 = createMock(Processor.class);
    this.pipeline.append(p1);

    final Message message = createMock(Message.class);
    p1.process(message);

    replay(p1);

    this.pipeline.process(message);

    verify(p1);
  }

  @Test
  public void testPipelineIdForwarding() throws Exception {
    final Processor p1 = createMock(Processor.class);
    this.pipeline.append(p1);

    final String id = "testId";

    expect(p1.getId()).andReturn(id);

    replay(p1);

    assertSame(id, this.pipeline.getId());

    verify(p1);
  }

}
