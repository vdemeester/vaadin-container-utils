package org.shortbrain.vaadin.container.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.shortbrain.vaadin.container.AbstractContainerUtilsTest;
import org.shortbrain.vaadin.container.annotation.Container;
import org.shortbrain.vaadin.container.annotation.ContainerType;
import org.shortbrain.vaadin.container.annotation.Property;

@SuppressWarnings("unused")
@RunWith(BlockJUnit4ClassRunner.class)
public class AnnotationReaderAlgorithmTest extends AbstractContainerUtilsTest {

	@Test
	public void getPropertiesNull() {
		try {
			AnnotationReaderAlgorithm a = new AnnotationReaderAlgorithm(null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void getPropertiesNonAnnotated() {
		AnnotationReaderAlgorithm a = new AnnotationReaderAlgorithm(
				ContainerType.RESUME);
		try {
			a.getProperties(NonAnnotatedBean.class);
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and its super classes are not annotated with Container.",
					e.getMessage());
		}
	}

	@Test
	public void getPropertiesEmpty() {
		AnnotationReaderAlgorithm a = new AnnotationReaderAlgorithm(
				ContainerType.RESUME);
		List<PropertyMetadata> metadatas = a
				.getProperties(EmptyContainerBean.class);
		assertNull(metadatas);
	}

	@Test
	public void getProperties() {
		AnnotationReaderAlgorithm a1 = new AnnotationReaderAlgorithm(
				ContainerType.RESUME);
		List<PropertyMetadata> metadatas1 = a1.getProperties(TestBean.class);
		assertNotNull(metadatas1);
		assertEquals(2, metadatas1.size());
		assertMetadata("string", String.class, null, "string",
				metadatas1.get(0));
		assertMetadata("number", Integer.class, null, "integer",
				metadatas1.get(1));

		AnnotationReaderAlgorithm a2 = new AnnotationReaderAlgorithm(
				ContainerType.EXTENDED);
		List<PropertyMetadata> metadatas2 = a2.getProperties(TestBean.class);
		assertNotNull(metadatas2);
		assertEquals(1, metadatas2.size());
		assertMetadata("string", String.class, null, "string",
				metadatas2.get(0));
	}

	private static class NonAnnotatedBean {
		private Date date;

		public Date getDate() {
			return date;
		}

	}

	@Container(properties = {})
	private static class EmptyContainerBean {
		private Date date;

		public Date getDate() {
			return date;
		}
	}

	@Container(properties = {
			@Property(name = "string", types = { ContainerType.EXTENDED,
					ContainerType.RESUME }),
			@Property(name = "number", types = { ContainerType.RESUME }, attribute = "integer") })
	private static class TestBean {

		private String string;
		private Integer integer;

		public String getString() {
			return string;
		}

		public Integer getInteger() {
			return integer;
		}

	}
}
