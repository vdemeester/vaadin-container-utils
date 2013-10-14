package org.xgbi.vaadin.container;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.IndexedContainer;

@RunWith(BlockJUnit4ClassRunner.class)
public class WrappedContainerTest {

	@Test
	public void constructor_with_null_should_fail_if_null() {
		try {
			new WrappedContainer(null);
		} catch (IllegalArgumentException e) {
			assertEquals("The container to be wrapped should not be null.",
					e.getMessage());
		}
	}
	
	@Test
	public void constructor_with_container() {
		ContainerWrapper container = new WrappedContainer(new IndexedContainer());
		assertThat(container).isNotNull().isInstanceOf(Container.class);
		assertThat(container.getContainer()).isInstanceOf(Indexed.class).isInstanceOf(IndexedContainer.class);
	}
	
}
