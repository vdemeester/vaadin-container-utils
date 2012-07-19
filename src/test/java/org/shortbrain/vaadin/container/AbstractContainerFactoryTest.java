package org.shortbrain.vaadin.container;

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
import org.shortbrain.vaadin.container.property.AttributeReaderAlgorithm;
import org.shortbrain.vaadin.container.property.PropertyReaderAlgorithm;

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
public class AbstractContainerFactoryTest extends AbstractContainerUtilsTest {

	private List<SimpleBean> beans;
	private List<SimpleHierarchicalBean> hierarchicalBeans;

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
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
		try {
			AbstractContainerFactory<SimpleBean> a = new AbstractContainerFactory<AbstractContainerFactoryTest.SimpleBean>(
					SimpleBean.class, null) {
			};
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
		try {
			AbstractContainerFactory<SimpleBean> a = new AbstractContainerFactory<AbstractContainerFactoryTest.SimpleBean>(
					null, new AttributeReaderAlgorithm()) {
			};
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals(
					"beanClass and propertyReaderAlgorithm cannot be null.",
					e.getMessage());
		}
	}

	@Test
	public void getContainerFromListContainerListNulls() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		Container c1 = s.getContainerFromList((Container) null, null);
		assertNotNull(c1);
		assertTrue(c1 instanceof Filterable);
		assertProperties(beanProperties, c1.getContainerPropertyIds());
		Container c2 = s.getContainerFromList(new IndexedContainer(), null);
		assertNotNull(c2);
		assertTrue(c2 instanceof Filterable);
		assertTrue(c2 instanceof IndexedContainer);
		assertProperties(beanProperties, c2.getContainerPropertyIds());
		Container c3 = s.getContainerFromList((Container) null, beans);
		assertNotNull(c3);
		assertTrue(c3 instanceof Filterable);
		assertProperties(beanProperties, c2.getContainerPropertyIds());
	}

	@Test
	public void getContainerFromListListContainerClassNulls() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		try {
			s.getContainerFromList((List<SimpleBean>) null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerClass cannot be null.", e.getMessage());
		}
		try {
			s.getContainerFromList(beans, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerClass cannot be null.", e.getMessage());
		}
		Container c2 = s.getContainerFromList(null, Hierarchical.class);
		assertNotNull(c2);
		assertTrue(c2 instanceof Hierarchical);
		assertProperties(beanProperties, c2.getContainerPropertyIds());
	}

	@Test
	public void getContainerFromListContainerListContainerClassNulls() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		try {
			s.getContainerFromList((Container) null, null, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerClass cannot be null.", e.getMessage());
		}
		try {
			s.getContainerFromList(null, beans, null);
			fail("should throw an IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			assertTrue(e instanceof IllegalArgumentException);
			assertEquals("containerClass cannot be null.", e.getMessage());
		}
		Container c2 = s.getContainerFromList(new IndexedContainer(), null,
				null);
		assertNotNull(c2);
		assertTrue(c2 instanceof IndexedContainer);
		assertProperties(beanProperties, c2.getContainerPropertyIds());
		Container c3 = s.getContainerFromList(new IndexedContainer(), beans,
				null);
		assertNotNull(c3);
		assertTrue(c3 instanceof IndexedContainer);
		assertProperties(beanProperties, c3.getContainerPropertyIds());
		Container c4 = s.getContainerFromList(new IndexedContainer(), null,
				Filterable.class);
		assertNotNull(c4);
		assertTrue(c4 instanceof Filterable);
		assertProperties(beanProperties, c4.getContainerPropertyIds());
		Container c6 = s.getContainerFromList(null, beans, Filterable.class);
		assertNotNull(c6);
		assertTrue(c6 instanceof Filterable);
		assertProperties(beanProperties, c6.getContainerPropertyIds());
		Container c7 = s.getContainerFromList(null, null, Filterable.class);
		assertNotNull(c7);
		assertTrue(c7 instanceof Filterable);
		assertProperties(beanProperties, c7.getContainerPropertyIds());
	}

	@Test
	public void getContainerFromListContainerList() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(new IndexedContainer(), beans);
		assertNotNull(c);
		assertEquals(IndexedContainer.class, c.getClass());
		assertProperties(beanProperties, c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(10, itemIds.size());
		for (int i = 0; i < 10; i++) {
			Item item = c.getItem(i + 1); // Vaadin start at 1
			Object[] values = new Object[] { "string" + i, i, beans.get(i) };
			assertItem(beanProperties, values, item);
		}
	}

	@Test
	public void getContainerFromListListContainerClass() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(beans, Filterable.class);
		assertNotNull(c);
		assertTrue(c instanceof Filterable);
		assertProperties(beanProperties, c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(10, itemIds.size());
		for (int i = 0; i < 10; i++) {
			Item item = c.getItem(i + 1); // Vaadin start at 1
			Object[] values = new Object[] { "string" + i, i, beans.get(i) };
			assertItem(beanProperties, values, item);
		}
	}

	@Test
	public void getContainerFromListContainerListContainerClass() {
		SimpleBeanContainerAttributeFactory s = new SimpleBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(new IndexedContainer(), beans,
				Filterable.class);
		assertNotNull(c);
		assertEquals(IndexedContainer.class, c.getClass());
		assertProperties(beanProperties, c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(10, itemIds.size());
		for (int i = 0; i < 10; i++) {
			Item item = c.getItem(i + 1); // Vaadin start at 1
			Object[] values = new Object[] { "string" + i, i, beans.get(i) };
			assertItem(beanProperties, values, item);
		}
	}

	@Test
	public void getContainerFromListContainerListHierarchical() {
		SimpleHierarchicalBeanContainerAttributeFactory s = new SimpleHierarchicalBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(new HierarchicalContainer(),
				hierarchicalBeans);
		assertNotNull(c);
		assertEquals(HierarchicalContainer.class, c.getClass());
		Hierarchical h = (Hierarchical) c;
		assertProperties(hierarchicalBeanProperties,
				c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(40, itemIds.size());
		for (int i = 0; i < 40; i = i + 3) {
			int reali = i / 4;
			Item item = c.getItem(i + 1); // Vaadin start at 1
			if (h.hasChildren(i + 1)) {
				Object[] values = new Object[] { "string" + reali, reali, null,
						hierarchicalBeans.get(reali) };
				assertItem(hierarchicalBeanProperties, values, item);
				Collection<?> children = h.getChildren(i + 1);
				for (int j = 0; j < 3; j++) {
					Item childItem = c.getItem(i + j + 2);
					Object[] childValues = new Object[] { "string" + reali + j,
							(reali * 10) + j, null, hierarchicalBeans.get(reali).getChildren().get(j) };
					assertItem(hierarchicalBeanProperties, childValues,
							childItem);
				}
			} else {
				// We won't test children right now.
				continue;
			}
		}
	}

	@Test
	public void getContainerFromListListContainerClassHierarchical() {
		SimpleHierarchicalBeanContainerAttributeFactory s = new SimpleHierarchicalBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(hierarchicalBeans, Hierarchical.class);
		assertNotNull(c);
		assertTrue(c instanceof Hierarchical);
		Hierarchical h = (Hierarchical) c;
		assertProperties(hierarchicalBeanProperties,
				c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(40, itemIds.size());
		for (int i = 0; i < 40; i = i + 3) {
			int reali = i / 4;
			Item item = c.getItem(i + 1); // Vaadin start at 1
			if (h.hasChildren(i + 1)) {
				Object[] values = new Object[] { "string" + reali, reali, null,
						hierarchicalBeans.get(reali) };
				assertItem(hierarchicalBeanProperties, values, item);
				Collection<?> children = h.getChildren(i + 1);
				for (int j = 0; j < 3; j++) {
					Item childItem = c.getItem(i + j + 2);
					Object[] childValues = new Object[] { "string" + reali + j,
							(reali * 10) + j, null, hierarchicalBeans.get(reali).getChildren().get(j) };
					assertItem(hierarchicalBeanProperties, childValues,
							childItem);
				}
			} else {
				// We won't test children right now.
				continue;
			}
		}
	}

	@Test
	public void getContainerFromListContainerListContainerClassHierarchical() {
		SimpleHierarchicalBeanContainerAttributeFactory s = new SimpleHierarchicalBeanContainerAttributeFactory();
		Container c = s.getContainerFromList(new HierarchicalContainer(), hierarchicalBeans, Hierarchical.class);
		assertNotNull(c);
		assertTrue(c instanceof Hierarchical);
		assertEquals(HierarchicalContainer.class, c.getClass());
		Hierarchical h = (Hierarchical) c;
		assertProperties(hierarchicalBeanProperties,
				c.getContainerPropertyIds());
		Collection<?> itemIds = c.getItemIds();
		assertEquals(40, itemIds.size());
		for (int i = 0; i < 40; i = i + 3) {
			int reali = i / 4;
			Item item = c.getItem(i + 1); // Vaadin start at 1
			if (h.hasChildren(i + 1)) {
				Object[] values = new Object[] { "string" + reali, reali, null,
						hierarchicalBeans.get(reali) };
				assertItem(hierarchicalBeanProperties, values, item);
				Collection<?> children = h.getChildren(i + 1);
				for (int j = 0; j < 3; j++) {
					Item childItem = c.getItem(i + j + 2);
					Object[] childValues = new Object[] { "string" + reali + j,
							(reali * 10) + j, null, hierarchicalBeans.get(reali).getChildren().get(j) };
					assertItem(hierarchicalBeanProperties, childValues,
							childItem);
				}
			} else {
				// We won't test children right now.
				continue;
			}
		}
	}

	protected static class SimpleBeanContainerAttributeFactory extends
			AbstractContainerFactory<SimpleBean> {

		public SimpleBeanContainerAttributeFactory() {
			super(SimpleBean.class, new AttributeReaderAlgorithm());
		}

	}

	private static final String[] beanProperties = new String[] { "string",
			"integer", "bean" };

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

	protected static class SimpleHierarchicalBeanContainerAttributeFactory
			extends AbstractContainerFactory<SimpleHierarchicalBean> {

		public SimpleHierarchicalBeanContainerAttributeFactory() {
			super(SimpleHierarchicalBean.class, new AttributeReaderAlgorithm());
		}

	}

	private static final String[] hierarchicalBeanProperties = new String[] {
			"string", "integer", "children", "bean" };

	public static class SimpleHierarchicalBean {
		private String string;
		private Integer integer;
		private List<SimpleHierarchicalBean> children;

		public SimpleHierarchicalBean(String string, Integer integer) {
			super();
			this.string = string;
			this.integer = integer;
			children = new LinkedList<AbstractContainerFactoryTest.SimpleHierarchicalBean>();
		}

		public String getString() {
			return string;
		}

		public Integer getInteger() {
			return integer;
		}

		public List<SimpleHierarchicalBean> getChildren() {
			return children;
		}

	}
}
