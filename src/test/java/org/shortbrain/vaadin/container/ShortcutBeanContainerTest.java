package org.shortbrain.vaadin.container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import org.shortbrain.vaadin.container.ShortcutBeanContainer;

import com.vaadin.data.util.AbstractBeanContainer.BeanIdResolver;

import static org.junit.Assert.assertEquals;

@RunWith(BlockJUnit4ClassRunner.class)
public class ShortcutBeanContainerTest {

    @Test
    public void test1() {
        BeanC c1 = new BeanC();
        c1.setStringC("stringC1");
        BeanC c2 = new BeanC();
        c2.setStringC("stringC2");
        BeanC c3 = new BeanC();
        c3.setStringC("stringC3");
        BeanB b1 = new BeanB();
        b1.setC(c1);
        b1.setStringB("stringB");
        BeanB b2 = new BeanB();
        b2.setC(c2);
        b2.setStringB("stringB");
        BeanB b3 = new BeanB();
        b3.setC(c3);
        b3.setStringB("stringB");
        BeanA a1 = new BeanA();
        a1.setB(b1);
        a1.setStringA("stringA1");
        BeanA a2 = new BeanA();
        a2.setB(b2);
        a2.setStringA("stringA2");
        BeanA a3 = new BeanA();
        a3.setB(b3);
        a3.setStringA("stringA3");
        BeanA a4 = new BeanA();
        a4.setB(b1);
        a4.setStringA("stringA4");
        ShortcutBeanContainer<BeanA, BeanA> c = new ShortcutBeanContainer<BeanA, BeanA>(BeanA.class);
        c.addNestedContainerProperty("b.stringB");
        c.addNestedContainerProperty("b.c");
        c.addNestedContainerProperty("b.c.stringC");
        c.addShortcutContainerProperty("stringB", "b.stringB");
        // c.addShortcutContainerProperty("stringC", "b.c.stringC");
        c.setBeanIdResolver(new BeanAIdResolver());
        c.addBean(a1);
        c.addBean(a2);
        c.addBean(a3);
        for (BeanA itemId : c.getItemIds()) {
            assertEquals("stringB", itemId.getB().getStringB());
            assertEquals("stringB", c.getItem(itemId).getItemProperty("stringB").getValue());
        }
    }

    public static class BeanAIdResolver implements BeanIdResolver<BeanA, BeanA> {

        @Override
        public BeanA getIdForBean(BeanA bean) {
            return bean;
        }

    }

    public static class BeanA {
        public String stringA;
        private BeanB b;

        public String getStringA() {
            return stringA;
        }

        public void setStringA(String stringA) {
            this.stringA = stringA;
        }

        public BeanB getB() {
            return b;
        }

        public void setB(BeanB b) {
            this.b = b;
        }

        @Override
        public boolean equals(Object obj) {
            return this.stringA.equals(((BeanA) obj).getStringA());
        }

    }

    public static class BeanB {
        private String stringB;
        private BeanC c;

        public String getStringB() {
            return stringB;
        }

        public void setStringB(String stringB) {
            this.stringB = stringB;
        }

        public BeanC getC() {
            return c;
        }

        public void setC(BeanC c) {
            this.c = c;
        }

    }

    public static class BeanC {
        private String stringC;

        public String getStringC() {
            return stringC;
        }

        public void setStringC(String stringC) {
            this.stringC = stringC;
        }

    }
}
