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
public class GetterReaderAlgorithmTest extends AbstractContainerUtilsTest {

	@Test
	public void getPropertiesNull() {
		GetterReaderAlgorithm g = new GetterReaderAlgorithm();
		try {
			g.getProperties(null);
			fail("should throw a IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void getProperties() {
		GetterReaderAlgorithm g = new GetterReaderAlgorithm();
		List<PropertyMetadata> metadatas = g.getProperties(TestBean.class);
		assertNotNull(metadatas);
		assertEquals(2, metadatas.size());
		assertMetadata("Integer", Integer.class, null, "Integer",
				metadatas.get(0));
		assertMetadata("String", String.class, null, "String", metadatas.get(1));
	}

	private static class SuperTestBean {
		private Date date;

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
