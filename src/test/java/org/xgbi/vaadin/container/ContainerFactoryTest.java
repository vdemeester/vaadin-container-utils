/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.xgbi.vaadin.container;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.xgbi.vaadin.container.ContainerFactory;
import org.xgbi.vaadin.container.annotation.Container;
import org.xgbi.vaadin.container.annotation.ContainerType;
import org.xgbi.vaadin.container.annotation.Property;
import org.xgbi.vaadin.container.property.PropertyMetadata;
import org.xgbi.vaadin.container.property.PropertyReaderAlgorithm;

import com.vaadin.data.util.IndexedContainer;

/**
 * Test class for {@link ContainerFactory}
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ContainerFactoryTest extends AbstractContainerUtilsTest {

	@Test
	public void testGetByAttributesNull() {
		try {
			ContainerFactory.getByAttributes(null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void testGetByAttributes() {
		ContainerFactory<SimpleBean> cf1 = ContainerFactory
				.getByAttributes(SimpleBean.class);
		assertNotNull(cf1);
		ContainerFactory<SimpleAnnotatedBean> cf2 = ContainerFactory
				.getByAttributes(SimpleAnnotatedBean.class);
		assertNotNull(cf2);
	}

	@Test
	public void testGetByGettersNull() {
		try {
			ContainerFactory.getByGetters(null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void testGetByGetters() {
		ContainerFactory<SimpleBean> cf1 = ContainerFactory
				.getByGetters(SimpleBean.class);
		assertNotNull(cf1);
		ContainerFactory<SimpleAnnotatedBean> cf2 = ContainerFactory
				.getByGetters(SimpleAnnotatedBean.class);
		assertNotNull(cf2);
	}

	@Test
	public void testGetByAnnotationNull() {
		try {
			ContainerFactory.getByAnnotation(null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerType cannot be null.", e.getMessage());
		}
		try {
			ContainerFactory.getByAnnotation(SimpleAnnotatedBean.class, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerType cannot be null.", e.getMessage());
		}
		try {
			ContainerFactory.getByAnnotation(null, ContainerType.EXTENDED);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void testGetByAnnotationInvalid() {
		// FIXME : Fix this behavior
		ContainerFactory<SimpleBean> cf = ContainerFactory.getByAnnotation(
				SimpleBean.class, ContainerType.EXTENDED);
		assertNotNull(cf);
		try {
			cf.getContainerFromCollection(new IndexedContainer(),
					Arrays.asList(new SimpleBean[] {}));
			fail("should throw an IllegalArgumentException.");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"the beanClass (or parent class) has to be annotated witch @Container.",
					e.getMessage());
		}
	}

	@Test
	public void testGetByAnnotation() {
		ContainerFactory<SimpleAnnotatedBean> cf = ContainerFactory
				.getByAnnotation(SimpleAnnotatedBean.class,
						ContainerType.EXTENDED);
		assertNotNull(cf);
	}

	@Test
	public void testGetByAlgorithmNull() {
		try {
			ContainerFactory.getByAlgorithm(null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
		try {
			ContainerFactory.getByAlgorithm(SimpleBean.class, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
		try {
			ContainerFactory.getByAlgorithm(null, new DumbReaderAlgorithm());
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void testGetByAlgorithm() {
		ContainerFactory<SimpleBean> cf = ContainerFactory.getByAlgorithm(
				SimpleBean.class, new DumbReaderAlgorithm());
		assertNotNull(cf);
	}

	private static class DumbReaderAlgorithm implements PropertyReaderAlgorithm {

		@Override
		public List<PropertyMetadata> getProperties(Class<?> beanClass) {
			return null;
		}

	}

	public static class SimpleBean {
		private String string;
		private Integer integer;

		public SimpleBean(String string, Integer integer) {
			super();
			this.string = string;
			this.integer = integer;
		}

		public String getString() {
			return string;
		}

		public Integer getInteger() {
			return integer;
		}

	}

	@Container(properties = {
			@Property(name = "string", types = { ContainerType.EXTENDED,
					ContainerType.RESUME }),
			@Property(name = "number", types = { ContainerType.RESUME }, attribute = "integer") })
	public static class SimpleAnnotatedBean {
		private String string;
		private Integer integer;

		public SimpleAnnotatedBean(String string, Integer integer) {
			super();
			this.string = string;
			this.integer = integer;
		}

		public String getString() {
			return string;
		}

		public Integer getInteger() {
			return integer;
		}

	}
}
