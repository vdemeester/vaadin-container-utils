package org.shortbrain.vaadin.container;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.shortbrain.vaadin.container.property.AttributeReaderAlgorithm;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Hierarchical;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.data.util.IndexedContainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AbstractContainerFactoryTestHelper extends AbstractContainerUtilsTest {

    protected IContainerFactory containerFactory;
    protected IContainerFactory hierarchicalContainerFactory;
    
    protected List<SimpleBean> beans;
    protected List<SimpleHierarchicalBean> hierarchicalBeans;

    @Test
    public void getContainerFromCollectionContainerListNulls() {
        Container c1 = containerFactory.getContainerFromCollection((Container) null, null);
        assertNotNull(c1);
        assertTrue(c1 instanceof Filterable);
        assertProperties(beanProperties, c1.getContainerPropertyIds());
        Container c2 = containerFactory.getContainerFromCollection(new IndexedContainer(), null);
        assertNotNull(c2);
        assertTrue(c2 instanceof Filterable);
        assertTrue(c2 instanceof IndexedContainer);
        assertProperties(beanProperties, c2.getContainerPropertyIds());
        Container c3 = containerFactory.getContainerFromCollection((Container) null, beans);
        assertNotNull(c3);
        assertTrue(c3 instanceof Filterable);
        assertProperties(beanProperties, c2.getContainerPropertyIds());
    }

    @Test
    public void getContainerFromCollectionListContainerClassNulls() {
        try {
            containerFactory.getContainerFromCollection((List<SimpleBean>) null, null);
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("containerClass cannot be null.", e.getMessage());
        }
        try {
            containerFactory.getContainerFromCollection(beans, null);
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("containerClass cannot be null.", e.getMessage());
        }
        Container c2 = containerFactory.getContainerFromCollection(null, Hierarchical.class);
        assertNotNull(c2);
        assertTrue(c2 instanceof Hierarchical);
        assertProperties(beanProperties, c2.getContainerPropertyIds());
    }

    @Test
    public void getContainerFromCollectionContainerListContainerClassNulls() {
        try {
            containerFactory.getContainerFromCollection((Container) null, null, null);
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("containerClass cannot be null.", e.getMessage());
        }
        try {
            containerFactory.getContainerFromCollection(null, beans, null);
            fail("should throw an IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("containerClass cannot be null.", e.getMessage());
        }
        Container c2 = containerFactory.getContainerFromCollection(new IndexedContainer(), null,
                null);
        assertNotNull(c2);
        assertTrue(c2 instanceof IndexedContainer);
        assertProperties(beanProperties, c2.getContainerPropertyIds());
        Container c3 = containerFactory.getContainerFromCollection(new IndexedContainer(), beans,
                null);
        assertNotNull(c3);
        assertTrue(c3 instanceof IndexedContainer);
        assertProperties(beanProperties, c3.getContainerPropertyIds());
        Container c4 = containerFactory.getContainerFromCollection(new IndexedContainer(), null,
                Filterable.class);
        assertNotNull(c4);
        assertTrue(c4 instanceof Filterable);
        assertProperties(beanProperties, c4.getContainerPropertyIds());
        Container c6 = containerFactory.getContainerFromCollection(null, beans, Filterable.class);
        assertNotNull(c6);
        assertTrue(c6 instanceof Filterable);
        assertProperties(beanProperties, c6.getContainerPropertyIds());
        Container c7 = containerFactory.getContainerFromCollection(null, null, Filterable.class);
        assertNotNull(c7);
        assertTrue(c7 instanceof Filterable);
        assertProperties(beanProperties, c7.getContainerPropertyIds());
    }

    @Test
    public void getContainerFromCollectionContainerList() {
        Container c = containerFactory.getContainerFromCollection(new IndexedContainer(), beans);
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
    public void getContainerFromCollectionListContainerClass() {
        Container c = containerFactory.getContainerFromCollection(beans, Filterable.class);
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
    public void getContainerFromCollectionContainerListContainerClass() {
        Container c = containerFactory.getContainerFromCollection(new IndexedContainer(), beans,
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
    public void getContainerFromCollectionContainerListHierarchical() {
        Container c = hierarchicalContainerFactory.getContainerFromCollection(new HierarchicalContainer(),
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
                            (reali * 10) + j, null,
                            hierarchicalBeans.get(reali).getChildren().get(j) };
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
    public void getContainerFromCollectionListContainerClassHierarchical() {
        Container c = hierarchicalContainerFactory.getContainerFromCollection(hierarchicalBeans,
                Hierarchical.class);
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
                            (reali * 10) + j, null,
                            hierarchicalBeans.get(reali).getChildren().get(j) };
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
    public void getContainerFromCollectionContainerListContainerClassHierarchical() {
        Container c = hierarchicalContainerFactory.getContainerFromCollection(new HierarchicalContainer(),
                hierarchicalBeans, Hierarchical.class);
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
                            (reali * 10) + j, null,
                            hierarchicalBeans.get(reali).getChildren().get(j) };
                    assertItem(hierarchicalBeanProperties, childValues,
                            childItem);
                }
            } else {
                // We won't test children right now.
                continue;
            }
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
            children = new LinkedList<SimpleHierarchicalBean>();
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
