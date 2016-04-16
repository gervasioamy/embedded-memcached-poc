# embedded-memcached-poc
Simple PoC for an embedded memcached server for testing proposes in a Java project

The main goal is to start a memecached deamon that will only be available during unti test are running.
In order to achieve that, these are the following technologies involved:

* Spring-Boot
* Mockito
* http://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html
* https://github.com/rdaum/jmemcache-daemon


#### Note
There's no other functionality than the unit test