package org.opennms.logcorrelator.core;

import org.opennms.logcorrelator.core.MessageDeclaratorImpl;
import java.lang.reflect.Method;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.opennms.logcorrelator.api.Message;
import org.opennms.logcorrelator.api.MessageAccessor;

public class MessageDeclaratorImplTest {
  public static class Foo {
  }


  public static class Bar {
  }


  public static class Base {
  }


  public static class Extended extends Base {
  }

  private MessageDeclaratorImpl impl;

  @Before
  public void setUp() throws Exception {
    this.impl = new MessageDeclaratorImpl();
  }

  @After
  public void tearDown() throws Exception {
    this.impl = null;
  }

  @Test
  public void testAcquireNew() throws Exception {
    final MessageAccessor<String> fooAccessor = impl.registerField("foo", String.class);

    assertNotNull(fooAccessor);

    assertEquals("foo", fooAccessor.getFieldName());
    assertEquals(String.class, fooAccessor.getFieldType());
  }

  @Test
  public void testAcquireExistingWithTypeMatch() throws Exception {
    final MessageAccessor<String> fooAccessor1 = impl.registerField("foo", String.class);
    final MessageAccessor<String> fooAccessor2 = impl.registerField("foo", String.class);

    assertNotNull(fooAccessor1);
    assertNotNull(fooAccessor2);

    assertEquals("foo", fooAccessor1.getFieldName());
    assertEquals("foo", fooAccessor2.getFieldName());

    assertEquals(String.class, fooAccessor1.getFieldType());
    assertEquals(String.class, fooAccessor2.getFieldType());

    final Message message = impl.createMessageImplementationClass().newInstance();

    assertNotNull(message);
  }

  @Test
  public void testAcquireExistingWithTypeUpCast() throws Exception {
    // First call declaring the field
    final MessageAccessor<Extended> fooAccessor1 = impl.registerField("foo", Extended.class);

    assertNotNull(fooAccessor1);

    assertEquals("foo", fooAccessor1.getFieldName());
    assertEquals(Extended.class, fooAccessor1.getFieldType());

    // Acquire the field with sub-class
    final MessageAccessor<Base> fooAccessor2 = impl.registerField("foo", Base.class);

    assertNotNull(fooAccessor2);

    assertEquals("foo", fooAccessor2.getFieldName());
    assertEquals(Extended.class, fooAccessor2.getFieldType());

    final Message message = impl.createMessageImplementationClass().newInstance();

    assertNotNull(message);
  }

  @Test(expected = ClassCastException.class)
  public void testAcquireExistingWithTypeMismatch() throws Exception {
    // First call declaring the field
    impl.registerField("foo", Foo.class);

    // Acquire the field with sub-class
    impl.registerField("foo", Bar.class);
  }

  @Test(expected = ClassCastException.class)
  public void testAcquireExistingWithTypeDownCast() throws Exception {
    // First call declaring the field
    impl.registerField("foo", Base.class);

    // Redeclaration with super-class type should fail
    impl.registerField("foo", Extended.class);
  }

  @Test
  public void testGetDefaultsWithNull() throws Exception {
    final MessageAccessor<String> fooAccessor = impl.registerField("foo", String.class);

    final Message message = impl.createMessageImplementationClass().newInstance();

    assertNotNull(message);

    assertNull(fooAccessor.get(message));
  }

  @Test
  public void testSetAndGet() throws Exception {
    final MessageAccessor<String> fooAccessor = impl.registerField("foo", String.class);
    final MessageAccessor<Integer> barAccessor = impl.registerField("bar", Integer.class);

    final Message message = impl.createMessageImplementationClass().newInstance();

    assertNotNull(message);

    message.set(fooAccessor, "snafu");
    message.set(barAccessor, 23);

    assertEquals("snafu", message.get(fooAccessor));
    assertEquals(23, (int) message.get(barAccessor));
  }

  @Test
  public void testSetAndGetWithUpCast() throws Exception {
    final MessageAccessor<Integer> fooAccessor1 = impl.registerField("foo", Integer.class);
    final MessageAccessor<Number> fooAccessor2 = impl.registerField("foo", Number.class);

    final Message message = impl.createMessageImplementationClass().newInstance();

    assertNotNull(message);

    final Integer value1 = new Integer(42);

    message.set(fooAccessor1, value1);

    final Number value2 = message.get(fooAccessor2);

    assertTrue(value1 == value2);
  }

  @Test
  public void testBeanGetter() throws Exception {
    final MessageAccessor<String> fooAccessor = impl.registerField("foo", String.class);

    final Class<Message> messageClass = impl.createMessageImplementationClass();

    assertNotNull(messageClass);

    final Method getter = messageClass.getMethod("getFoo");

    assertNotNull(getter);
    assertEquals(String.class, getter.getReturnType());
    assertEquals(0, getter.getParameterTypes().length);
  }

  @Test
  public void testBeanSetter() throws Exception {
    impl.registerField("foo", String.class);

    final Class<Message> messageClass = impl.createMessageImplementationClass();

    assertNotNull(messageClass);

    final Method setter = messageClass.getMethod("setFoo", String.class);

    assertNotNull(setter);
    assertEquals(void.class, setter.getReturnType());
    assertEquals(1, setter.getParameterTypes().length);
    assertEquals(String.class, setter.getParameterTypes()[0]);
  }

}
