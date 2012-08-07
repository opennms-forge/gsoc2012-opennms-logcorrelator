package org.opennms.logcorrelator.api;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
import org.junit.Test;


public class PreprocessorTest {
//  private Processor nextProcessorMock;
//
//  @Override
//  protected void setUp() throws Exception {
//    this.nextProcessorMock = createMock(Processor.class);
//  }
//
//  @Override
//  protected void tearDown() throws Exception {
//  }
//
//  @Test
//  public void testChaining() throws Exception {
//    final Message message1 = new Message();
//    final Message message2 = new Message();
//
//    final Map<String, Pattern> filters = new HashMap<String, Pattern>();
//    final Preprocessor preprocessor = new Preprocessor(filters) {
//      @Override
//      protected void transmogrify(Message message) {
//        this.pass(message2);
//      }
//
//    };
//    preprocessor.setNextProcessor(nextProcessorMock);
//
//    // Alternative message will be passed instead of original
//    nextProcessorMock.process(message2);
//
//    replay(nextProcessorMock);
//    preprocessor.process(message1);
//    verify(nextProcessorMock);
//  }
//
//  @Test
//  public void testFilterPass() throws Exception {
//    final Message message1 = new Message();
//    final Message message2 = new Message();
//    
//    final Message.Accessor<String> fooAccessor = message1.acquire("foo", String.class);
//    message1.set(fooAccessor, "match this");
//
//    final Map<String, Pattern> filters = new HashMap<String, Pattern>();
//    filters.put("foo", Pattern.compile("match this"));
//    
//    final Preprocessor preprocessor = new Preprocessor(filters) {
//      @Override
//      protected void transmogrify(Message message) {
//        this.pass(message2);
//      }
//
//    };
//    preprocessor.setNextProcessor(nextProcessorMock);
//
//    // Alternative message will be passed instead of original
//    nextProcessorMock.process(message2);
//
//    replay(nextProcessorMock);
//    preprocessor.process(message1);
//    verify(nextProcessorMock);
//  }
//
//  @Test
//  public void testFilterFail() throws Exception {
//    final Message message1 = new Message();
//    final Message message2 = new Message();
//    
//    final Message.Accessor<String> fooAccessor = message1.acquire("foo", String.class);
//    message1.set(fooAccessor, "match this");
//
//    final Map<String, Pattern> filters = new HashMap<String, Pattern>();
//    filters.put("foo", Pattern.compile("does not match"));
//    
//    final Preprocessor preprocessor = new Preprocessor(filters) {
//      @Override
//      protected void transmogrify(Message message) {
//        this.pass(message2);
//      }
//
//    };
//    preprocessor.setNextProcessor(nextProcessorMock);
//
//    // Original message will be passed instead of alternative one
//    nextProcessorMock.process(message1);
//
//    replay(nextProcessorMock);
//    preprocessor.process(message1);
//    verify(nextProcessorMock);
//  }
//
//  @Test
//  public void testFilterFailUndeclared() throws Exception {
//    final Message message1 = new Message();
//    final Message message2 = new Message();
//    
//    final Message.Accessor<String> fooAccessor = message1.acquire("foo", String.class);
//    message1.set(fooAccessor, "match this");
//
//    final Map<String, Pattern> filters = new HashMap<String, Pattern>();
//    filters.put("foo", Pattern.compile("match this"));
//    filters.put("bar", Pattern.compile("match this"));
//    
//    final Preprocessor preprocessor = new Preprocessor(filters) {
//      @Override
//      protected void transmogrify(Message message) {
//        this.pass(message2);
//      }
//
//    };
//    preprocessor.setNextProcessor(nextProcessorMock);
//
//    // Original message will be passed instead of alternative one
//    nextProcessorMock.process(message1);
//
//    replay(nextProcessorMock);
//    preprocessor.process(message1);
//    verify(nextProcessorMock);
//  }
//
//  @Test
//  public void testFilterFailOneOfThree() throws Exception {
//    final Message message1 = new Message();
//    final Message message2 = new Message();
//    
//    final Message.Accessor<String> fooAccessor = message1.acquire("foo", String.class);
//    message1.set(fooAccessor, "match this");
//    
//    final Message.Accessor<String> barAccessor = message1.acquire("bar", String.class);
//    message1.set(barAccessor, "match this");
//    
//    final Message.Accessor<String> bazAccessor = message1.acquire("baz", String.class);
//    message1.set(bazAccessor, "match this");
//
//    final Map<String, Pattern> filters = new HashMap<String, Pattern>();
//    filters.put("foo", Pattern.compile("match this"));
//    filters.put("bar", Pattern.compile("match this"));
//    filters.put("baz", Pattern.compile("does not match"));
//    
//    final Preprocessor preprocessor = new Preprocessor(filters) {
//      @Override
//      protected void transmogrify(Message message) {
//        this.pass(message2);
//      }
//
//    };
//    preprocessor.setNextProcessor(nextProcessorMock);
//
//    // Original message will be passed instead of alternative one
//    nextProcessorMock.process(message1);
//
//    replay(nextProcessorMock);
//    preprocessor.process(message1);
//    verify(nextProcessorMock);
//  }
  
}
