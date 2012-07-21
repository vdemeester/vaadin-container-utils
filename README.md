# vaadin-container-utils

vaadin-container-utils is a util library to facilitate the creation of Containers.

<table>
  <tr>
    <td>Author</td>
    <td>Vincent Demeester (vincent+github@demeester.fr)</td>
  </tr>
  <tr>
    <td>Version</td>
    <td>0.1.0-SNAPSHOT</td>
  </tr>
  <tr>
    <td>Status</td>
    <td>master (0.1.0) : <img src="https://secure.travis-ci.org/vdemeester/vaadin-container-utils.png?branch=master" alt="Build status (master)" /><br />
    develop (0.2.0-SNAPSHOT) : <img src="https://secure.travis-ci.org/vdemeester/vaadin-container-utils.png?branch=develop" alt="Build status (develop)" /></td>
  </tr>
</table>

It currently contains two main classes to use :

* __ContainerUtils__ : Utils class useful for common and generic operation on Container such as initialize, add a property, etc.
* __ContainerFactory__ : Create different type of Container from a list of Bean using different algorithm to get the list of properties.

It also contains a ``@Container`` annotation to easily annotate your beans for behavior 
in creating ]Vaadin](http://vaadin.com) containers. The corresponding ``PropertyReaderAlogrithm``
is then ``AnnotationReaderAlgorithm``.

The goals when creating this library is to ease the creation of different type of containers,
while still have a maximum flexibility. Before having a ``1.0`` version, there is a lot of
improvement to push (_Pivotable_ containers, better customization, etc.).

### Using

To use in your [Maven](http://maven.apache.org) powered project, you'll have to add
the temporary shortbrain repository (for now… soon in the vaadin addon repository and/or
the central repository)

	```xml
	<repositories>
	  <repository>
	    <id>shortbrain-releases</id>
	    <url>https://raw.github.com/shortbrain/shortbrain-tmp-mvn-repository/master/releases</url>
	  </repository>
	</repositories>
	```
	
## Usage

### ContainerUtils

The main method in ``ContainerUtils`` is ``initContainer``. It lets you instantiate a new Container based on a type.
If the type is a concrete class, it will directly instantiate it, else it will try some predefined default.

	```java
	// Concrete classes
	ContainerUtils.initContainer(IndexedContainer.class); // Instantiate an IndexedContainer
	// Interfaces
	ContainerUtils.initContainer(Filterable.class); // Instantiate a Filterable container (using IndexedContainer)
	ContainerUtils.initContainer(Hierarchical.class); // Instantiate a Hierarchical container (using HierarchicalContainer)
	```

For more, please look at the [javadocs](/apidocs/org/shortbrain/vaadin/container/ContainerUtils.html)

### ContainerFactory

The ``ContainerFactory`` class is here to simplify the creation of Containers. 
It will handle the initialization of the Container and its properties (and update them if
the container already contained properties), and populate it with a given list of Object (Bean).

	// ContainerFactory containerFactory already defined
	// Specify an existing Container (container), and a list of MyBean
	containerFactory.getContainerFromList(container, myBeans);
	
	// Specify an existing null Container, and a list of MyBean
	Container.Filterable filterableContainer = null;
	containerFactory.getContainerFromList(filterableContainer, myBeans, Filterable.class);
	
	// Specify a list of MyBean and a type of Container
	Container.Filterable filterableContainer = null;
	containerFactory.getContainerFromList(myBeans, Filterable.class);

Right now, ``ContainerFactory`` only support ``Filterable`` and ``Hierarchical`` Vaadin containers.
For ``Hierarchical`` containers, it will by default look for an attribute named ``children``. In the 
future version of this library, this behavior will be configurable).

	class MyBean {
		List<MyBean> children;
		//[…]
	}
	// Specify a list of MyBean and a type of Container
	Container.Filterable filterableContainer = null;
	containerFactory.getContainerFromList(myBeans, Hierarchical.class);
	// This will give you a hierarchy (visible in TreeTable for example)

### Creation
	
There is sensible default static creation methods to get ContainerFactory instances.

	// Simple default ContainerFactory
	// Looking for properties by attributes (of MyBean)
	ContainerFactory<MyBean> containerFactory = ContainerFactory.getByAttributes(MyBean.class);
	
	// Looking for properties by getter methods (of MyBean)
	ContainerFactory<MyBean> containerFactory = ContainerFactory.getByGetters(MyBean.class);

It also possible to passed a custom ``PropertyReaderAlgorithm``, implementation that will get 
properties out of MyBean.

	// Let say TotoAlgorithm is an implementation of PropertyReaderAlgorithm
	ContainerFactory<MyBean> containerFactory = ContainerFactory.getByAlgorithm(MyBean.class, new TotoAlgorithm());

#### Annotation

The last algorithm currently implemented is by looking up for the ``@Container`` annotation.
The idea behind this annotation is to be able to specify the properties that should be created
for the bean and how to get the values.

For this to work, there is an ``@Container`` annotation that takes an array of ``@Property`` annotation.
Each ``@Property`` defines few fields :

* __name__ : The name of the properties.
* __types__ : An array of ``ContainerType`` that lets defined multiple type of Container.
* __attribut__ : How to look for the value of the property. If not specified, it will be the same as name.

<pre>
@Container(properties = {
		@Property(name = "string", types = { ContainerType.EXTENDED,
				ContainerType.RESUME }),
		@Property(name = "number", types = { ContainerType.RESUME }, attribute = "integer") })
private static class TestBean {

	private String string;
	private Integer integer;

	public String getString() {
		return string;
	}

	public Integer getInteger() {
		return integer;
	}
}
</pre>

As for other implemented algorithms, there is an easy way to get a ContainerFactory using
this algorithm.

	// Looking for properties by annotation @Container (of MyBean)
	// Using ContainerType.RESUME definition
	ContainerFactory<MyBean> containerFactory = ContainerFactory.getByAnnotation(MyBean.class, ContainerType.RESUME);
	// Using ContainerType.EXTENDED definition
	ContainerFactory<MyBean> containerFactory = ContainerFactory.getByAnnotation(MyBean.class, ContainerType.EXTENDED);

### Extending

The ContainerFactory has been designed to be easily extensible. There is two way of extending the ContainerFactory :

1. Extends the ``ContainerFactory`` class. This is almost like implementing an Interface, you have almost nothing to start.
2. Extends the ``AbstractContainerFactory``, the class behind the static creation method. This is the __preferred__ ways.