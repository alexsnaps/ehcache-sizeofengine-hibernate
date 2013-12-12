ehcache-sizeofengine-hibernate3
===============================

See [Ehcache's SizeOfEngine module](http://terracotta-oss.github.io/ehcache-sizeofengine/) for details, but to put it
simply: adding this dependency to your pom.xml, will have [Ehcache](http://www.ehcache.org)'s Automatic Resource
Control (aka [ARC](http://ehcache.org/documentation/arc)) size Hibernate4 caches correctly.

Maven dependency
----------------

	<dependency>
		<groupId>net.sf.ehcache</groupId>
		<artifactId>sizeofengine-hibernate</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>