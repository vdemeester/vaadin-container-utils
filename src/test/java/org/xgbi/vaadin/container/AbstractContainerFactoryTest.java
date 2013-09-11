/*
 * Copyright 2012 Vincent Demeester<vincent+shortbrain@demeester.fr>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.xgbi.vaadin.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.xgbi.vaadin.container.AbstractContainerFactory;
import org.xgbi.vaadin.container.property.AttributeReaderAlgorithm;
import org.xgbi.vaadin.container.property.PropertyReaderAlgorithm;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;

/**
 * Test class for {@link AbstractContainerFactory}.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
@SuppressWarnings("unused")
@RunWith(BlockJUnit4ClassRunner.class)
public class AbstractContainerFactoryTest extends AbstractContainerFactoryTestHelper {

    @Before
	public void before() {
		beans = new LinkedList<SimpleBean>();
		for (int i = 0; i < 10; i++) {
			SimpleBean bean = new SimpleBean("string" + i, i);
			beans.add(bean);
		}
		hierarchicalBeans = new LinkedList<SimpleHierarchicalBean>();
		for (int i = 0; i < 10; i++) {
			SimpleHierarchicalBean bean = new SimpleHierarchicalBean("string"
					+ i, i);
			for (int j = 0; j < 3; j++) {
				bean.getChildren().add(
						new SimpleHierarchicalBean("string" + i + j, (i * 10)
								+ j));
			}
			hierarchicalBeans.add(bean);
		}
		containerFactory = new SimpleBeanContainerAttributeFactory();
		hierarchicalContainerFactory = new SimpleHierarchicalBeanContainerAttributeFactory();
	}

    @Test
    public void abstractContainerFactoryNulls() {
        try {
            AbstractContainerFactory<SimpleBean> a = new AbstractContainerFactory<AbstractContainerFactoryTest.SimpleBean>(
                    null, null) {
            };
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("beanClass and propertyReaderAlgorithm cannot be null.", e.getMessage());
        }
        try {
            AbstractContainerFactory<SimpleBean> a = new AbstractContainerFactory<AbstractContainerFactoryTest.SimpleBean>(
                    SimpleBean.class, null) {
            };
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("beanClass and propertyReaderAlgorithm cannot be null.", e.getMessage());
        }
        try {
            AbstractContainerFactory<SimpleBean> a = new AbstractContainerFactory<AbstractContainerFactoryTest.SimpleBean>(
                    null, new AttributeReaderAlgorithm()) {
            };
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("beanClass and propertyReaderAlgorithm cannot be null.", e.getMessage());
        }
    }

    protected static class SimpleBeanContainerAttributeFactory extends AbstractContainerFactory<SimpleBean> {

        public SimpleBeanContainerAttributeFactory() {
            super(SimpleBean.class, new AttributeReaderAlgorithm());
        }

    }

    protected static class SimpleHierarchicalBeanContainerAttributeFactory extends
            AbstractContainerFactory<SimpleHierarchicalBean> {

        public SimpleHierarchicalBeanContainerAttributeFactory() {
            super(SimpleHierarchicalBean.class, new AttributeReaderAlgorithm());
        }

    }
}
