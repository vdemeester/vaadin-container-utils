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
package org.xgbi.vaadin.container.property;

import java.util.List;


/**
 * Property Reader algorithm interface, used in ContainerFactory to build the
 * Property of the container.
 * 
 * @author Vincent Demeester <vincent@demeester.fr>
 * 
 */
public interface PropertyReaderAlgorithm {

	List<PropertyMetadata> getProperties(Class<?> beanClass);
}
