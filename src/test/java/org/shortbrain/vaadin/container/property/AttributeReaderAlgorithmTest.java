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
package org.shortbrain.vaadin.container.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.shortbrain.vaadin.container.AbstractContainerUtilsTest;

/**
 * Test class for {@link AttributeReaderAlgorithm}.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
@SuppressWarnings("unused")
@RunWith(BlockJUnit4ClassRunner.class)
public class AttributeReaderAlgorithmTest extends AbstractContainerUtilsTest {

	@Test
	public void getPropertiesNull() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm();
		try {
			a.getProperties(null);
			fail("should throw a IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("beanClass cannot be null.", e.getMessage());
		}
	}

	@Test
	public void getPropertiesIgnoredNull() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(null);
		List<PropertyMetadata> metadatas = a.getProperties(SuperTestBean.class);
		assertNotNull(metadatas);
		assertEquals(1, metadatas.size());
		assertMetadata("date", Date.class, null, "date", metadatas.get(0));
	}

	@Test
	public void getPropertiesDefault() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm();
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(3, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
		assertMetadata("date", Date.class, null, "date", metadatas.get(2));
	}

	@Test
	public void getPropertiesWithSuper() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(true);
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(3, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
		assertMetadata("date", Date.class, null, "date", metadatas.get(2));
	}

	@Test
	public void getPropertiesWithoutSuper() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(false);
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(2, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
	}

	@Test
	public void getPropertiesWithIgnored() {
		AttributeReaderAlgorithm a1 = new AttributeReaderAlgorithm(
				Arrays.asList(new String[] { "date" }));
		List<PropertyMetadata> metadatas1 = a1.getProperties(TestBean.class);
		assertNotNull(metadatas1);
		assertEquals(2, metadatas1.size());
		assertMetadata("string", String.class, null, "string",
				metadatas1.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas1.get(1));
		AttributeReaderAlgorithm a2 = new AttributeReaderAlgorithm(
				Arrays.asList(new String[] { "string" }));
		List<PropertyMetadata> metadatas2 = a2.getProperties(TestBean.class);
		assertNotNull(metadatas2);
		assertEquals(2, metadatas2.size());
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas2.get(0));
		assertMetadata("date", Date.class, null, "date", metadatas2.get(1));
	}

	@Test
	public void getPropertiesWithSuperAndIgnored() {
		AttributeReaderAlgorithm a1 = new AttributeReaderAlgorithm(
				Arrays.asList(new String[] { "date" }), true);
		List<PropertyMetadata> metadatas1 = a1.getProperties(TestBean.class);
		assertNotNull(metadatas1);
		assertEquals(2, metadatas1.size());
		assertMetadata("string", String.class, null, "string",
				metadatas1.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas1.get(1));
		AttributeReaderAlgorithm a2 = new AttributeReaderAlgorithm(
				Arrays.asList(new String[] { "string" }), true);
		List<PropertyMetadata> metadatas2 = a2.getProperties(TestBean.class);
		assertNotNull(metadatas2);
		assertEquals(2, metadatas2.size());
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas2.get(0));
		assertMetadata("date", Date.class, null, "date", metadatas2.get(1));
	}

	@Test
	public void getPropertiesWithSuperAndWithoutIgnored() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(null, true);
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(3, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
		assertMetadata("date", Date.class, null, "date", metadatas.get(2));
	}

	@Test
	public void getPropertiesWithoutSuperAndIgnored() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(
				Arrays.asList(new String[] { "date" }), false);
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(2, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
	}

	@Test
	public void getPropertiesWithoutSuperAndWithoutIgnored() {
		AttributeReaderAlgorithm a = new AttributeReaderAlgorithm(null, false);
		List<PropertyMetadata> metadatas = a.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(2, metadatas.size());
		assertMetadata("string", String.class, null, "string", metadatas.get(0));
		assertMetadata("integer", Integer.class, null, "integer",
				metadatas.get(1));
	}

	private static class SuperTestBean {
		private Date date;

		public Date getDate() {
			return date;
		}

	}

	private static class TestBean extends SuperTestBean {

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
