## About

vaadin-container-utils is a util library to facilitate the creation of Containers.

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

	<repositories>
	  <repository>
	    <id>shortbrain-releases</id>
	    <url>https://raw.github.com/shortbrain/shortbrain-tmp-mvn-repository/master/releases</url>
	  </repository>
	</repositories>
