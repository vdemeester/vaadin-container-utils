package org.shortbrain.vaadin.container.annotation.reader;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.shortbrain.vaadin.container.AbstractContainerUtilsTest;
import org.shortbrain.vaadin.container.ContainerUtilsTest;

@SuppressWarnings("unused")
@RunWith(BlockJUnit4ClassRunner.class)
public class BeanAbstractAnnotationReaderTest extends
		AbstractContainerUtilsTest {

	@Test
	public void BeanAbstractAnnotationReaderNulls() {
		try {
			BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
					null, null) {
			};
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("originalBeanClass and beanClass cannot be null.",
					e.getMessage());
		}
		try {
			BeanAbstractAnnotationReader<Object> b2 = new BeanAbstractAnnotationReader<Object>(
					Object.class, null) {
			};
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("originalBeanClass and beanClass cannot be null.",
					e.getMessage());
		}
		try {
			BeanAbstractAnnotationReader<Object> b3 = new BeanAbstractAnnotationReader<Object>(
					null, Object.class) {
			};
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("originalBeanClass and beanClass cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void staticGetAnnotatedClassNull() {
		try {
			BeanAbstractAnnotationReader.getAnnotatedClass(null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("beanClass and annotationClass cannot be null.",
					e.getMessage());
		}
		try {
			BeanAbstractAnnotationReader.getAnnotatedClass(
					SimpleAnnotatedBean.class, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("beanClass and annotationClass cannot be null.",
					e.getMessage());
		}
		try {
			BeanAbstractAnnotationReader.getAnnotatedClass(null,
					SimpleAnnotation.class);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("beanClass and annotationClass cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void BeanAbstractAnnotationReader() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		assertNotNull(b1);
		assertEquals(SimpleAnnotatedBean.class, b1.getOriginalBeanClass());
		assertEquals(SimpleAnnotatedBean.class, b1.getBeanClass());
	}

	@Test
	public void BeanAbstractAnnotationReaderSuper() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SubSimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		assertNotNull(b1);
		assertEquals(SubSimpleAnnotatedBean.class, b1.getOriginalBeanClass());
		assertEquals(SimpleAnnotatedBean.class, b1.getBeanClass());
	}

	@Test
	public void staticGetAnnotatedClassNonAnnotated() {
		try {
			BeanAbstractAnnotationReader.getAnnotatedClass(
					NonAnnotatedBean.class, SimpleAnnotation.class);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and its super classes are not annotated with SimpleAnnotation.",
					e.getMessage());
		}
	}

	@Test
	public void staticGetAnnotatedClass() {
		Class<?> klass1 = BeanAbstractAnnotationReader.getAnnotatedClass(
				SimpleAnnotatedBean.class, SimpleAnnotation.class);
		assertNotNull(klass1);
		assertEquals(SimpleAnnotatedBean.class, klass1);
		Class<?> klass2 = BeanAbstractAnnotationReader.getAnnotatedClass(
				SubSimpleAnnotatedBean.class, SimpleAnnotation.class);
		assertNotNull(klass1);
		assertEquals(SimpleAnnotatedBean.class, klass1);
	}

	@Test
	public void getPropertyTypeNull() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		try {
			b1.getPropertyType(null);
			fail("should generate a NoSuchFieldException");
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			assertTrue(e instanceof NoSuchFieldException);
		}
	}

	@Test
	public void getPropertyTypeEmpty() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		try {
			b1.getPropertyType(null);
			fail("should generate a NoSuchFieldException");
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			assertTrue(e instanceof NoSuchFieldException);
		}
	}

	@Test
	public void getPropertyTypeInvalid() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		try {
			b1.getPropertyType("toto");
			fail("should generate a NoSuchFieldException");
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			assertTrue(e instanceof NoSuchFieldException);
		}
	}

	@Test
	public void getPropertyType() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		try {
			Class<?> propertyType = b1.getPropertyType("string");
			assertNotNull(propertyType);
			assertEquals(String.class, propertyType);
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		}
		try {
			Class<?> propertyType = b1
					.getPropertyType("nonAnnotatedBean.subString");
			assertNotNull(propertyType);
			assertEquals(String.class, propertyType);
		} catch (SecurityException e) {
			fail(e.getMessage());
		} catch (NoSuchFieldException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void getFieldNulls() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		try {
			b1.getField(null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("klass or fieldName cannot be null.", e.getMessage());
		}
		try {
			b1.getField(null, "string");
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("klass or fieldName cannot be null.", e.getMessage());
		}
		try {
			b1.getField(SimpleAnnotatedBean.class, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("klass or fieldName cannot be null.", e.getMessage());
		}
	}

	@Test
	public void getFieldInexistent() {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		Field f = b1.getField(SimpleAnnotatedBean.class, "inexistent");
		assertNull(f);
	}

	@Test
	public void getField() throws NoSuchFieldException, SecurityException {
		BeanAbstractAnnotationReader<Object> b1 = new BeanAbstractAnnotationReader<Object>(
				SimpleAnnotatedBean.class, SimpleAnnotatedBean.class) {
		};
		Field f = b1.getField(SimpleAnnotatedBean.class, "string");
		assertNotNull(f);
		assertEquals(SimpleAnnotatedBean.class.getDeclaredField("string"), f);
	}

	@Target({ TYPE })
	@Retention(RUNTIME)
	private static @interface SimpleAnnotation {
	}

	@SimpleAnnotation
	private static class SimpleAnnotatedBean {
		private String string;
		private NonAnnotatedBean nonAnnotatedBean;

		public String getString() {
			return string;
		}

		public void setString(String string) {
			this.string = string;
		}

	}

	private static class SubSimpleAnnotatedBean extends SimpleAnnotatedBean {
	}

	private static class NonAnnotatedBean {
		private String subString;
	}

}
