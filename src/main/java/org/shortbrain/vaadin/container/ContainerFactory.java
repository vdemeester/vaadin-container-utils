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
package org.shortbrain.vaadin.container;

import java.util.List;

import com.vaadin.data.Container;

/**
 * ContainerFactory interface that define methods to facilitate the creation of
 * Containers from a list of objects using different algorithms.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 * @param <BEAN>
 *            type of the beans.
 */
public interface ContainerFactory<BEAN> {

	/**
	 * Return a container of type BEAN from a list of BEAN objects. It will
	 * update the given container if no null.
	 * 
	 * @param container
	 *            container to be populated.
	 * @param beans
	 *            list of beans.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(Container container, List<BEAN> beans);

	/**
	 * Return a container of type BEAN from a list of BEAN objects. It will
	 * update the given container if no null.
	 * 
	 * The returned container will be of the given type (containerClass).
	 * 
	 * @param container
	 *            container to be populated.
	 * @param beans
	 *            list of beans.
	 * @param containerClass
	 *            type of the container to return.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(Container container,
			List<BEAN> beans, Class<? extends Container> containerClass);

	/**
	 * Return a container of type BEAN from a list of BEAN objects.
	 * 
	 * The returned container will be of the given type (containerClass).
	 * 
	 * @param beans
	 *            list of beans.
	 * @param containerClass
	 *            type of the container to return.
	 * @return a container of Beans.
	 */
	public Container getContainerFromList(List<BEAN> beans,
			Class<? extends Container> containerClass);
}
